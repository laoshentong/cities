package com.graph.cities.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.graph.cities.exceptions.CitiesServiceException;

public class CitiesConnectionUtilTest {
	
	@InjectMocks
	private CitiesConnectionUtil citiesUtil;
	
	private Map<String, Set<String>> citiesMap;
	
	@Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void init() {
		citiesMap = createCitiesmap();
	}
	
	@Test
	public void testLoadCities() throws IOException{
		String file = "/cities.txt";
		String delimiter = ",";
		
		assertThat(citiesUtil.loadCities(file, delimiter), notNullValue());
		assertThat(citiesUtil.loadCities(file, delimiter).size(), equalTo(11));
		assertThat(citiesUtil.loadCities(file, delimiter).containsKey("Tampa"), is(true));
		assertThat(citiesUtil.loadCities(file, delimiter).get("Tampa").iterator().next(), is("St. Petersburg"));
		assertThat(citiesUtil.loadCities(file, delimiter).get("Philadelphia").size(), is(2));
	}
	
	@Test(expected = CitiesServiceException.class)
	public void testLoadCitiesWithException() throws IOException {
		String file = "/citie";
		String delimiter = ",";
		
		citiesUtil.loadCities(file, delimiter);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testDelimiter() throws IOException {
		String file = "/cities.txt";
		String delimiter = ";";
		
		citiesUtil.loadCities(file, delimiter);
	}
	
	@Test
	public void testBFS() {
		
		assertThat(citiesUtil.doBFS(citiesMap, "Toronto", "Boston"), is(true));
		assertThat(citiesUtil.doBFS(citiesMap, "Toronto", "New York"), is(true));
		assertThat(citiesUtil.doBFS(citiesMap, "New York", "Philadelphia"), is(true));
		assertThat(citiesUtil.doBFS(citiesMap, "Philadelphia", "Boston"), is(true));
		assertThat(citiesUtil.doBFS(citiesMap, "Renton", "Seattle"), is(true));
		assertThat(citiesUtil.doBFS(citiesMap, "Philadelphia", "Renton"), is(false));
		assertThat(citiesUtil.doBFS(citiesMap, "Toronto", "Seattle"), is(false));
		assertThat(citiesUtil.doBFS(citiesMap, "New York", "Renton"), is(false));
		assertThat(citiesUtil.doBFS(citiesMap, "Boston", "Seattle"), is(false));
		assertThat(citiesUtil.doBFS(citiesMap, "Philadelphia", "Seattle"), is(false));
		
		
	}
	
	@Test
	public void testDFS() {
		
		assertThat(citiesUtil.doDFS(citiesMap, "Toronto", "Boston"), is(true));
		assertThat(citiesUtil.doDFS(citiesMap, "Toronto", "New York"), is(true));
		assertThat(citiesUtil.doDFS(citiesMap, "New York", "Philadelphia"), is(true));
		assertThat(citiesUtil.doDFS(citiesMap, "Philadelphia", "Boston"), is(true));
		assertThat(citiesUtil.doDFS(citiesMap, "Renton", "Seattle"), is(true));
		assertThat(citiesUtil.doDFS(citiesMap, "Philadelphia", "Renton"), is(false));
		assertThat(citiesUtil.doDFS(citiesMap, "Toronto", "Seattle"), is(false));
		assertThat(citiesUtil.doDFS(citiesMap, "New York", "Renton"), is(false));
		assertThat(citiesUtil.doDFS(citiesMap, "Boston", "Seattle"), is(false));
		assertThat(citiesUtil.doDFS(citiesMap, "Philadelphia", "Seattle"), is(false));
		
		
	}
	//connected cities: {{"Boston","New York"},{"Philadelphia","Toronto"},{"Toronto","Boston"},{"Seattle","Renton"}}
	private Map<String, Set<String>> createCitiesmap(){
		Map<String, Set<String>> citiesMap = new HashMap<String, Set<String>>();
		Set<String> newYorkConnections = new HashSet<String>();
		newYorkConnections.add("Boston");
		citiesMap.put("New York", newYorkConnections);
		
		Set<String> bostonConnections = new HashSet<String>();
		bostonConnections.add("New York");
		bostonConnections.add("Toronto");
		citiesMap.put("Boston", bostonConnections);
		
		Set<String> torontoConnections = new HashSet<String>();
		torontoConnections.add("Philadelphia");
		torontoConnections.add("Boston");
		citiesMap.put("Toronto", torontoConnections);
		
		Set<String> seattleConnections = new HashSet<String>();
		seattleConnections.add("Renton");
		citiesMap.put("Seattle", seattleConnections);
		
		Set<String> rentonConnections = new HashSet<String>();
		rentonConnections.add("Seattle");
		citiesMap.put("Renton", rentonConnections);
		
		Set<String> philadelphiaConnections = new HashSet<String>();
		philadelphiaConnections.add("Philadelphia");
		citiesMap.put("Philadelphia", torontoConnections);
		
		return citiesMap;
	}

}
