package PustakaLokam.library.service;

import PustakaLokam.library.dao.BookDAO;
import PustakaLokam.library.dao.IssueRecordDAO;
import PustakaLokam.library.dao.MemberDAO;
import PustakaLokam.library.model.Book;
import PustakaLokam.library.model.IssueRecord;
import PustakaLokam.library.model.Member;
import PustakaLokam.library.enums.IssueStatus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
    private final IssueRecordDAO issueDao = new IssueRecordDAO();
    private final BookDAO bookDao = new BookDAO();
    private final MemberDAO memberDao = new MemberDAO();

    public List<IssueRecord> getOverdueBooks() {
        try {
            List<IssueRecord> allIssues = issueDao.findAllIssues();
            return allIssues.stream()
                    .filter(issue -> issue.getStatus() == IssueStatus.I)
                    .filter(issue -> issue.getIssueDate().plusDays(30).isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Map<String, Long> getBookCountByCategory() {
        try {
            List<Book> allBooks = bookDao.getAllBooks();
            return allBooks.stream()
                    .collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public List<Member> getMembersWithActiveIssues() {
        List<Member> result = new ArrayList<>();

        try {
            List<IssueRecord> allIssues = issueDao.findAllIssues();

            List<Integer> memberIds = allIssues.stream()
                .filter(issue -> issue.getStatus() == IssueStatus.I)
                .map(IssueRecord::getMemberID)
                .collect(Collectors.toList());
                try {
                    return memberDao.getMemberByID(memberIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


}
