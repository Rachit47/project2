package com.springbootproject.librarium365.exceptions;

public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3791094941172091678L;

	public BookNotFoundException(String message) {
		super(message);
	}
}