package com.springbootproject.librarium365.exceptions;

public class InvalidEmailException extends Exception {
	private static final long serialVersionUID = 5334897541848672602L;

	@Override
	public String getMessage() {
		return "Email is not in proper format, it should end with @gmail.com !";
	}
}