package com.graph.cities.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.graph.cities.service.impl.CitiesConnectionFinderServiceImpl;

public class CitiesControllerTest {
	@Mock
	private CitiesConnectionFinderServiceImpl citiesService;

	@InjectMocks
	private CitiesController handler;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}
	

	@Test
	public void testCitiesNoConnection() {
		Mockito.when(citiesService.isConnected(any(), any())).thenReturn(false);
		
		assertThat(handler.areCitiesConnected(any(), any()), notNullValue());
		assertThat(handler.areCitiesConnected(any(), any()), equalTo("no"));
	}
	
	@Test
	public void testCitiesConnected() {
		Mockito.when(citiesService.isConnected(any(), any())).thenReturn(true);
		
		assertThat(handler.areCitiesConnected(any(), any()), notNullValue());
		assertThat(handler.areCitiesConnected(any(), any()), equalTo("yes"));
		
	}
}
