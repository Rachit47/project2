package com.springbootproject.librarium365.model;

import com.springbootproject.librarium365.enums.IssueStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class IssueBook {

	private Integer issueID;

	@NotBlank(message = "Book ID is required")
	private Integer bookID;

	@NotBlank(message = "Member ID is required")
	private Integer memberID;

	@NotBlank(message = "Issue status is required")
	private IssueStatus status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Please provide a date.")
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
