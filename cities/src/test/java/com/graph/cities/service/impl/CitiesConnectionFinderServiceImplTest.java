package com.graph.cities.service.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.graph.cities.configuration.CitiesConfiguration;
import com.graph.cities.exceptions.InvalidRequestException;
import com.graph.cities.util.CitiesConnectionUtil;

public class CitiesConnectionFinderServiceImplTest {
	
	@Mock
	private CitiesConnectionUtil citiesConnectionUtil;
	
	@Mock
	private CitiesConfiguration citiesConfiguration;
	
	@Mock
	private Map<String, Set<String>> map;
	
	@Mock
	private Set<String> set;
	
	@Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	private CitiesConnectionFinderServiceImpl citiesConnectionService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void testIsConnectedFalseWithBFS() throws IOException {
		
		map = new HashMap<String, Set<String>>();
		map.put("ttest", new HashSet<String>());
		map.put("test", new HashSet<String>());
		
		doReturn(map).when(citiesConnectionUtil).loadCities(any(), any());
		
		Mockito.when(citiesConfiguration.getAlgorithm()).thenReturn("BFS");
		Mockito.when(citiesConnectionUtil.doBFS(any(), any(), any())).thenReturn(false);
		boolean isConnected = citiesConnectionService.isConnected("ttest", "test");
		
		assertThat(isConnected, notNullValue());
		assertThat(isConnected, equalTo(false));
	}
	
	@Test
	public void testIsConnectedTrueWithBFS() throws IOException {
		
		map = new HashMap<String, Set<String>>();
		map.put("ttest", new HashSet<String>());
		map.put("test", new HashSet<String>());
		
		doReturn(map).when(citiesConnectionUtil).loadCities(any(), any());
		
		Mockito.when(citiesConfiguration.getAlgorithm()).thenReturn("BFS");
		Mockito.when(citiesConnectionUtil.doBFS(any(), any(), any())).thenReturn(true);
		boolean isConnected = citiesConnectionService.isConnected("ttest", "test");
		
		assertThat(isConnected, notNullValue());
		assertThat(isConnected, equalTo(true));
	}
	
	@Test
	public void testIsConnectedFalseWithDFS() throws IOException {
		map = new HashMap<String, Set<String>>();
		map.put("ttest", new HashSet<String>());
		map.put("test", new HashSet<String>());
		
		doReturn(map).when(citiesConnectionUtil).loadCities(any(), any());
		
		Mockito.when(citiesConfiguration.getAlgorithm()).thenReturn("DFS");
		Mockito.when(citiesConnectionUtil.doDFS(any(), any(), any())).thenReturn(false);
		boolean isConnected = citiesConnectionService.isConnected("ttest", "test");
		
		assertThat(isConnected, notNullValue());
		assertThat(isConnected, equalTo(false));
	}
	
	@Test
	public void testIsConnectedTrueWithDFS() throws IOException {
		map = new HashMap<String, Set<String>>();
		map.put("ttest", new HashSet<String>());
		map.put("test", new HashSet<String>());
		
		doReturn(map).when(citiesConnectionUtil).loadCities(any(), any());
		
		Mockito.when(citiesConfiguration.getAlgorithm()).thenReturn("DFS");
		Mockito.when(citiesConnectionUtil.doDFS(any(), any(), any())).thenReturn(true);
		boolean isConnected = citiesConnectionService.isConnected("ttest", "test");
		
		assertThat(isConnected, notNullValue());
		assertThat(isConnected, equalTo(true));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testIsConnectedWithException() {
		boolean isConnected = citiesConnectionService.isConnected("River", "River");
	}
}
