package com.graph.cities.exceptions;

/**
 * Represents an Exception encountered while retrieving connections between cities.
 * 
 */
@SuppressWarnings("serial")
public class CitiesServiceException extends RuntimeException{
	
	public CitiesServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CitiesServiceException(Throwable cause) {
		super(cause);
	}

	public CitiesServiceException(String message) {
		super(message);
	}
	
}
