package com.graph.cities.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.graph.cities.configuration.CitiesConfiguration;
import com.graph.cities.exceptions.CitiesServiceException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class CitiesConnectionUtil {
	
	// create citiesMap from file.
	// The key in the map are the cities that we have available.
	// The value is a set of cities that this city is connected to
	// for each line, add two key-value pairs for both directions of possible traversal
	public Map<String, Set<String>> loadCities(String fileName, String delimiter) throws IOException{
		log.info("start loadCities");
		log.debug("fileName is "+fileName);
		Map<String, Set<String>> citiesMap = new HashMap<String, Set<String>>();

		BufferedReader bufferedReader = null;
		try {
			Resource resource = new ClassPathResource(fileName);
			InputStream resourceAsStream = resource.getInputStream();
			Reader fileReader = new InputStreamReader(resourceAsStream);
			bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			while (line != null && !line.isEmpty()) {
				String[] cities = line.split(delimiter);
				String firstCity = cities[0].trim();
				String secondCity = cities[1].trim();

				Set<String> firstCityConnections = buildConnectedCities(citiesMap, firstCity);
				Set<String> secondCityConnections = buildConnectedCities(citiesMap, secondCity);
				firstCityConnections.add(secondCity);
				secondCityConnections.add(firstCity);

				line = bufferedReader.readLine();
			}
		}catch(FileNotFoundException fnfe) {
			log.error("file not found", fnfe);
			throw new CitiesServiceException("File not found.");
		}finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		log.info("loadCities End");
		return citiesMap;
	}

	// helper function to default the connected cities in the map to the empty set
	private Set<String> buildConnectedCities(Map<String, Set<String>> map, String city) {
		log.debug("in buildConnectedCities, city is "+city);
		if (!map.containsKey(city)) {
			log.debug("city is not in map yet");
			map.put(city, new HashSet<String>());
		}
		return map.get(city);
	}
	
	
	public boolean doBFS(Map<String, Set<String>> citiesMap, String cityOne, String cityTwo) {
		log.info("start doBFS");
		boolean isFound = false;
		// By using a Queue, we are implementing Breadth First Search
		Queue<String> citiesToVisit = new LinkedList<String>();

		// We keep a set of the cities we have already visited. This prevents BFS from looping in
		// cycles and allows the BFS to terminate if no path can be found after exploring all reachable nodes
		Set<String> citiesAlreadyVisited = new HashSet<String>();

		citiesToVisit.add(cityOne);

		while (!citiesToVisit.isEmpty() && !isFound) {
			
			String city = citiesToVisit.poll();
			log.debug("polled city is "+city);
			isFound = city.equals(cityTwo);

			if(isFound) return isFound;
			log.debug("not found yet, continue to search.");	
			Set<String> possibleConnections = citiesMap.get(city);
			for (String possibleCity : possibleConnections) {
				if (!citiesAlreadyVisited.contains(possibleCity)) {
					citiesToVisit.add(possibleCity);
					citiesAlreadyVisited.add(possibleCity);
				}
			}
		}
		log.info("doBFS End");
		return isFound;
	}
	
	
	public boolean doDFS(Map<String, Set<String>> citiesMap, String cityOne, String cityTwo) {
		log.info("start doDFS");
		boolean isFound = false;
		// By using a stack, we are implementing Depth First Search
		Stack<String> citiesToVisit = new Stack<String>();

		// We keep a set of the cities we have already visited. This prevents DFS from looping in
		// cycles and allows the DFS to terminate if no path can be found after exploring all reachable nodes
		Set<String> citiesAlreadyVisited = new HashSet<String>();

		citiesToVisit.push(cityOne);

		while (!citiesToVisit.isEmpty() && !isFound) {
			String city = citiesToVisit.pop();
			log.debug("poped city is "+city);
			isFound = city.equals(cityTwo);
				
			if(isFound) return isFound;
			log.debug("not found yet, continue to search.");	
			Set<String> possibleConnections = citiesMap.get(city);
			for (String possibleCity : possibleConnections) {
				if (!citiesAlreadyVisited.contains(possibleCity)) {
					citiesToVisit.push(possibleCity);
					citiesAlreadyVisited.add(possibleCity);
				}
			}
		}
		log.info("doDFS End");
		return isFound;
	}
	
}
