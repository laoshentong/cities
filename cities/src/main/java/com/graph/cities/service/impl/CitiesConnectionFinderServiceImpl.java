package com.graph.cities.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.graph.cities.configuration.CitiesConfiguration;
import com.graph.cities.exceptions.CitiesServiceException;
import com.graph.cities.exceptions.InvalidRequestException;
import com.graph.cities.service.ICitiesConnectionFinderService;
import com.graph.cities.util.CitiesConnectionUtil;
import com.graph.cities.util.SearchAlgorithm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class CitiesConnectionFinderServiceImpl implements ICitiesConnectionFinderService{
	
	private static final String DELIMITER =",";
	
	private CitiesConnectionUtil connectionUtil;
	
	private CitiesConfiguration citiesConfiguration;
	
	@Override
	public boolean isConnected(String firstCity, String secondCity) {
		log.info("start isConnected in service");
		log.debug("firstCity is "+firstCity+" secondCity is "+secondCity);
		//cities should be different
		if(firstCity.equals(secondCity)) throw new InvalidRequestException("two cities should not be same.");
		
		boolean isConnected = false;
		try {
			Map<String, Set<String>> citiesMap = connectionUtil.loadCities(citiesConfiguration.getFile(), DELIMITER);
			//check if input cities is not in map, these two cities are not connected 
			if (citiesMap.containsKey(firstCity) && citiesMap.containsKey(secondCity)) {
				if(SearchAlgorithm.BFS == SearchAlgorithm.valueOf(citiesConfiguration.getAlgorithm())) {
					isConnected = connectionUtil.doBFS(citiesMap, firstCity, secondCity);
				}else {
					isConnected = connectionUtil.doDFS(citiesMap, firstCity, secondCity);
				}
			}
		}catch(IOException ie) {
			throw new CitiesServiceException("Error when reading file.");
		}
		log.info("isConnected in service End");
		return isConnected;
	}
	


}
