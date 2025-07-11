package com.springbootproject.librarium365.exceptions;

public class IssueOperationException extends Exception {

	private static final long serialVersionUID = 2119110202053572421L;

	public IssueOperationException(String message) {
		super(message);
	}

	public IssueOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}