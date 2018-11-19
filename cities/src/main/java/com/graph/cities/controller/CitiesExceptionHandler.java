package com.graph.cities.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.graph.cities.exceptions.CitiesServiceException;
import com.graph.cities.exceptions.InvalidRequestException;
import com.graph.cities.gen.model.Error.IdEnum;
import com.graph.cities.gen.model.Error.SeverityEnum;
import com.graph.cities.gen.model.Error;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CitiesExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Handle the BadRequestException
	 * 
	 * @param ex the BadRequestException
	 * @param request the web request
	 * @return the bad request response
	 */
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(
			final InvalidRequestException ex, 
			final WebRequest request) {
		logger.error("Bad Request",ex);
		return handleExceptionInternal(
				ex, 
				null,
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST, 
				request);
	}
	
	/**
	 * Handle CitiesServiceException
	 * 
	 * @param ex the BadRequestException
	 * @param request the web request
	 * @return the bad request response
	 */
	@ExceptionHandler(CitiesServiceException.class)
	public ResponseEntity<Object> handleServiceException(
			final CitiesServiceException ex, 
			final WebRequest request) {
		logger.error("Error in cities service",ex);
		return handleExceptionInternal(
				ex, 
				new Error().id(IdEnum.CITIES_SERVICE_ERROR).message(ex.getMessage()).severity(SeverityEnum.SEVERE),
				new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, 
				request);
	}
	
	/**
	 * Maps unknown exceptions to a Response Entity object.
	 * 
	 * @param ex
	 *            The unknown exception to map
	 * @param request
	 *            Request made when the error was thrown
	 * @return The response entity containing the mapped exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {

		logger.error("unkown error",ex);
		return handleExceptionInternal(ex,
				new Error().id(IdEnum.UNKNOWN_ERROR).message("Unknown error").severity(SeverityEnum.SEVERE),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
}
