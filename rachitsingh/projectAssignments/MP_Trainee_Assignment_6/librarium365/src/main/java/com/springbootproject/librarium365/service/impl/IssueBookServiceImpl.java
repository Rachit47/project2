package com.springbootproject.librarium365.service.impl;

import com.springbootproject.librarium365.enums.AvailabilityStatus;
import com.springbootproject.librarium365.exceptions.*;
import com.springbootproject.librarium365.model.Book;
import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.repository.impl.BookDAOImpl;
import com.springbootproject.librarium365.repository.impl.IssueBookDAOImpl;
import com.springbootproject.librarium365.repository.impl.MemberDAOImpl;
import com.springbootproject.librarium365.service.IssueBookService;
import com.springbootproject.librarium365.config.DBConnectionConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class IssueBookServiceImpl implements IssueBookService {

    private final IssueBookDAOImpl issueDao;
    private final BookDAOImpl bookDao;
    private final MemberDAOImpl memberDao;
    private final DBConnectionConfig dbConnectionConfig;

    @Autowired
    public IssueBookServiceImpl(IssueBookDAOImpl issueDao,
                                BookDAOImpl bookDao,
                                MemberDAOImpl memberDao,
                                DBConnectionConfig dbConnectionConfig) {
        this.issueDao = issueDao;
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.dbConnectionConfig = dbConnectionConfig;
    }

    @Override
    public IssueBook issueBook(Integer bookID, Integer memberID)
            throws BookNotFoundException, MemberNotFoundException, IssueOperationException {

        Connection conn = null;
        try {
            conn = dbConnectionConfig.getConnection();
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

            IssueBook rec = new IssueBook(bookID, memberID);
            issueDao.insertIssue(rec, conn);
            bookDao.updateBookAvailability(bookID, AvailabilityStatus.ISSUED, conn);

            conn.commit();
            return rec;

        } catch (SQLException | RuntimeException ex) {
            rollbackQuietly(conn);
            throw new IssueOperationException("Failed to issue book ID " + bookID, ex);
        } finally {
            closeQuietly(conn);
        }
    }

    @Override
    public void returnBook(int issueID) throws IssueNotFoundException, IssueOperationException {
        Connection conn = null;
        try {
            conn = dbConnectionConfig.getConnection();
            conn.setAutoCommit(false);

            IssueBook rec = issueDao.findByID(issueID, conn);

            issueDao.markReturnedBook(issueID, conn);
            bookDao.updateBookAvailability(rec.getBookID(), AvailabilityStatus.AVAILABLE, conn);

            conn.commit();
        } catch (IssueNotFoundException infe) {
            rollbackQuietly(conn);
            throw infe;
        } catch (SQLException | RuntimeException ex) {
            rollbackQuietly(conn);
            throw new IssueOperationException("Failed to return issue ID " + issueID, ex);
        } finally {
            closeQuietly(conn);
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignore) {
            }
        }
    }
}
