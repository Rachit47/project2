package library.service;

import java.util.List;
import java.util.Map;

import library.model.IssueBook;
import library.model.Member;

public interface ReportServiceInterface {
	public List<IssueBook> getOverdueBooks();
	public Map<String, Long> getBookCountByCategory();
	public List<Member> getMembersWithActiveIssues();
}
