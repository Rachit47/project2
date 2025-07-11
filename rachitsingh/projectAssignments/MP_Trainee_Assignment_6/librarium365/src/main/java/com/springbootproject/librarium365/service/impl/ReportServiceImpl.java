package com.springbootproject.librarium365.service.impl;

import com.springbootproject.librarium365.repository.BookDAO;
import com.springbootproject.librarium365.repository.IssueBookDAO;
import com.springbootproject.librarium365.repository.MemberDAO;
import com.springbootproject.librarium365.enums.IssueStatus;
import com.springbootproject.librarium365.model.Book;
import com.springbootproject.librarium365.model.IssueBook;
import com.springbootproject.librarium365.model.Member;
import com.springbootproject.librarium365.service.ReportService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final IssueBookDAO issueDao;
    private final BookDAO bookDao;
    private final MemberDAO memberDao;

    public ReportServiceImpl(IssueBookDAO issueDao, BookDAO bookDao, MemberDAO memberDao) {
        this.issueDao = issueDao;
        this.bookDao = bookDao;
        this.memberDao = memberDao;
    }

    @Override
    public List<IssueBook> getOverdueBooks() {
        try {
            List<IssueBook> allIssues = issueDao.findAllIssues();
            return allIssues.stream()
                    .filter(issue -> issue.getStatus() == IssueStatus.ISSUED)
                    .filter(issue -> issue.getIssueDate().plusDays(30).isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
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

    @Override
    public List<Member> getMembersWithActiveIssues() {
        try {
            List<IssueBook> allIssues = issueDao.findAllIssues();

            List<Integer> memberIds = allIssues.stream()
                    .filter(issue -> issue.getStatus() == IssueStatus.ISSUED)
                    .map(IssueBook::getMemberID)
                    .distinct()
                    .collect(Collectors.toList());

            return memberDao.getMemberByID(memberIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
