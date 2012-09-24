package org.none.toka.util.handlers.path;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public NotFoundException(String message) {
		super(message);
	}
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}