package com.example.employee_management_application.exception;

public class CustomCreateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomCreateException(String message) {
		super(message);
	}
}