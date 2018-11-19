package com.graph.cities.exceptions;

/**
 * Represents an Exception encountered while incoming request is bad.
 */
@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException{

	public InvalidRequestException(final String message) {
		super(message);
	}

	public InvalidRequestException(final Throwable cause) {
		super(cause);
	}

	public InvalidRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
