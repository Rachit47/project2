package com.springbootproject.librarium365.exceptions;

public class MemberNotFoundException extends Exception {

	private static final long serialVersionUID = 6270813288029204648L;

	public MemberNotFoundException(String message) {
		super(message);
	}

	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
