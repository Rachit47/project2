package library.model;

import library.enums.IssueStatus;
import java.time.LocalDate;

public class IssueBook {
	private Integer issueID;
	private Integer bookID;
	private Integer memberID;
	private IssueStatus status;
	private LocalDate issueDate;

	public IssueBook() {
	}

	public IssueBook(Integer bookID, Integer memberID) {
		this.bookID = bookID;
		this.memberID = memberID;
		this.status = IssueStatus.ISSUED;
		this.issueDate = LocalDate.now();
	}

	public IssueBook(Integer issueID, Integer bookID, Integer memberID, IssueStatus status, LocalDate issueDate) {
		this.issueID = issueID;
		this.bookID = bookID;
		this.memberID = memberID;
		this.status = status;
		this.issueDate = issueDate;
	}

	public Integer getIssueID() {
		return issueID;
	}

	public void setIssueID(Integer issueID) {
		this.issueID = issueID;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public String toString() {
		return "IssueBook{" + "issueID=" + issueID + ", bookID=" + bookID + ", memberID=" + memberID + ", status="
				+ status + ", issueDate=" + issueDate + '}';
	}
}