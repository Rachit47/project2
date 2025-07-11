package PustakaLokam.library.service;

import PustakaLokam.library.dao.BookDAO;
import PustakaLokam.library.dao.IssueRecordDAO;
import PustakaLokam.library.dao.MemberDAO;
import PustakaLokam.library.enums.AvailabilityStatus;
import PustakaLokam.library.exceptionhandler.*;
import PustakaLokam.library.model.Book;
import PustakaLokam.library.model.IssueRecord;
import PustakaLokam.library.model.Member;
import PustakaLokam.library.utilities.DBConnectivityUtility;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class IssueRecordService {
    private final IssueRecordDAO issueDao = new IssueRecordDAO();
    private final BookDAO bookDao = new BookDAO();
    private final MemberDAO memberDao = new MemberDAO();

    public IssueRecord issueBook(int bookID, int memberID)
            throws BookNotFoundException, MemberNotFoundException, IssueOperationException {
        Connection conn = null;
        try {
            conn = DBConnectivityUtility.getConnection();
            conn.setAutoCommit(false);

            Book book = bookDao.getBookByID(bookID);
            if (book == null) {
                throw new BookNotFoundException("Book ID " + bookID + " not found.");
            }
            if (book.getAvailability() != AvailabilityStatus.AVAILABLE) {
                throw new IssueOperationException("Book ID " + bookID + " is currently not available.");
            }

            List<Member> members = memberDao.getMemberByID(Collections.singletonList(memberID));
            if (members == null || members.isEmpty()) {
                throw new MemberNotFoundException("Member ID " + memberID + " not found.");
            }

            IssueRecord rec = new IssueRecord(bookID, memberID);
            issueDao.insertIssue(rec, conn);

            bookDao.updateBookAvailability(bookID, AvailabilityStatus.ISSUED, conn);

            conn.commit();
            return rec;
        } catch (SQLException | RuntimeException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            throw new IssueOperationException("Failed to issue book ID " + bookID, ex);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignore) {}
            }
        }
    }

    public void returnBook(int issueID) 
            throws IssueNotFoundException, IssueOperationException {
        Connection conn = null;
        try {
            conn = DBConnectivityUtility.getConnection();
            conn.setAutoCommit(false);

            IssueRecord rec = issueDao.findByID(issueID, conn);

            // Mark the issue as returned and log it
            issueDao.markReturnedBook(issueID, conn);

            // Update book status back to AVAILABLE
            bookDao.updateBookAvailability(rec.getBookID(), AvailabilityStatus.AVAILABLE, conn);

            conn.commit();
        } catch (IssueNotFoundException infe) {
            rollbackQuietly(conn);
            throw infe;
        } catch (SQLException | RuntimeException ex) {
            rollbackQuietly(conn);
            throw new IssueOperationException("Failed to return issue ID " + issueID, ex);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignore) {}
            }
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
