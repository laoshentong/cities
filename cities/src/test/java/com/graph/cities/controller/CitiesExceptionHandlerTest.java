package com.graph.cities.controller;

//import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.graph.cities.exceptions.CitiesServiceException;
import com.graph.cities.exceptions.InvalidRequestException;
import com.graph.cities.gen.model.Error;

public class CitiesExceptionHandlerTest {
	
	private CitiesExceptionHandler handler;
	
	@Mock
	private WebRequest request;
	
	@Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void init() {
		handler = new CitiesExceptionHandler();
		
		Mockito.doNothing().when(request).setAttribute(anyString(), any(Object.class), anyInt());
	}
	
	@Test
	public void testHandleUnknownException() {
		Exception throwable = new Exception("error");
		ResponseEntity<Object> entity = handler.handleUnknownException(throwable, request);
		
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		assertThat(entity.getBody(), instanceOf(Error.class));
		assertThat(((Error)entity.getBody()).getId(), is(Error.IdEnum.UNKNOWN_ERROR));
	}
	
	@Test
	public void testHandleCardControlBadRequestException() {
		InvalidRequestException throwable = new InvalidRequestException("");
		ResponseEntity<Object> entity = handler.handleBadRequestException(throwable, request);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void testHandleCitiesServiceException() {
		CitiesServiceException throwable = new CitiesServiceException("error");
		ResponseEntity<Object> entity = handler.handleServiceException(throwable, request);
		
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		assertThat(entity.getBody(), instanceOf(Error.class));
		assertThat(((Error)entity.getBody()).getId(), is(Error.IdEnum.CITIES_SERVICE_ERROR));
	}

}
