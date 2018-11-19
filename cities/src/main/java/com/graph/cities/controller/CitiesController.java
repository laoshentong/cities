package com.graph.cities.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graph.cities.service.ICitiesConnectionFinderService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class CitiesController {
	
	ICitiesConnectionFinderService citiesService;
	
	@RequestMapping(value = "/connected")
	public String areCitiesConnected(@RequestParam(name="origin", required = true) String origin, @RequestParam(name="destination", required = true) String destination) {
		boolean isConnected = citiesService.isConnected(origin, destination);
		if(isConnected) return "yes";
		return "no";
	}

}
