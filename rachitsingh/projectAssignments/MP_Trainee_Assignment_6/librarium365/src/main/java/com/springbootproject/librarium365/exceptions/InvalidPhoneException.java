package com.springbootproject.librarium365.exceptions;

public class InvalidPhoneException extends Exception {
	private static final long serialVersionUID = 8436815976023644179L;

	@Override
	public String getMessage() {
		return "Phone should match exactly,10 digits number only!";
	}
}