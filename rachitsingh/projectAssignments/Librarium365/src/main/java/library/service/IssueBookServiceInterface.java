package library.service;

import library.exceptionhandler.BookNotFoundException;
import library.exceptionhandler.IssueNotFoundException;
import library.exceptionhandler.IssueOperationException;
import library.exceptionhandler.MemberNotFoundException;
import library.model.IssueBook;

public interface IssueBookServiceInterface {
	 IssueBook issueBook(Integer bookID, Integer memberID)
	            throws BookNotFoundException, MemberNotFoundException, IssueOperationException;
	 void returnBook(int issueID)
	            throws IssueNotFoundException, IssueOperationException;

}
