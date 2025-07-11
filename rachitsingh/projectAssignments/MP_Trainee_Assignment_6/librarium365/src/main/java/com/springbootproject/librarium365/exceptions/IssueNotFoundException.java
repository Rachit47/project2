package com.springbootproject.librarium365.exceptions;

public class IssueNotFoundException extends Exception {
	private static final long serialVersionUID = -1071039153762798458L;

	public IssueNotFoundException(String message) {
		super(message);
	}
}
