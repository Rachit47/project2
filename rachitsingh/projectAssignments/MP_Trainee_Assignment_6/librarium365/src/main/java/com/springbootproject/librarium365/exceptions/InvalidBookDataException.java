package com.springbootproject.librarium365.exceptions;

public class InvalidBookDataException extends RuntimeException {
	private static final long serialVersionUID = -5794309532565406136L;

	public InvalidBookDataException(String message) {
		super(message);
	}
}