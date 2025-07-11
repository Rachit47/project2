package library.service;

import library.dao.BookDAO;
import library.dao.IssueBookDAO;
import library.dao.MemberDAO;
import library.model.Book;
import library.model.IssueBook;
import library.model.Member;
import library.enums.IssueStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService implements ReportServiceInterface{
	private final IssueBookDAO issueDao = new IssueBookDAO();
	private final BookDAO bookDao = new BookDAO();
	private final MemberDAO memberDao = new MemberDAO();
	
	@Override
	public List<IssueBook> getOverdueBooks() {
		try {
			List<IssueBook> allIssues = issueDao.findAllIssues();
			return allIssues.stream().filter(issue -> issue.getStatus() == IssueStatus.ISSUED)
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
			return allBooks.stream().collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}
	
	@Override
	public List<Member> getMembersWithActiveIssues() {
		try {
			List<IssueBook> allIssues = issueDao.findAllIssues();

			List<Integer> memberIds = allIssues.stream().filter(issue -> issue.getStatus() == IssueStatus.ISSUED)
					.map(IssueBook::getMemberID).distinct().collect(Collectors.toList());

			return memberDao.getMemberByID(memberIds);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}
}
