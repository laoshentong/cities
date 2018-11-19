package com.graph.cities.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "com.graph.cities")
@Data
public class CitiesConfiguration {
	
	private String algorithm;
	private String file;
	

}
