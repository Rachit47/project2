package com.springbootproject.librarium365.service;

import com.springbootproject.librarium365.exceptions.*;
import com.springbootproject.librarium365.model.IssueBook;

public interface IssueBookService {

	IssueBook issueBook(Integer bookID, Integer memberID)
			throws BookNotFoundException, MemberNotFoundException, IssueOperationException;

	void returnBook(int issueID) throws IssueNotFoundException, IssueOperationException;
}
