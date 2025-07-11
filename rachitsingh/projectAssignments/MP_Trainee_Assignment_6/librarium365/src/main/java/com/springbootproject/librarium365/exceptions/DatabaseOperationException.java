package com.springbootproject.librarium365.exceptions;

public class DatabaseOperationException extends RuntimeException {

	private static final long serialVersionUID = -4062764334607268554L;

	public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}