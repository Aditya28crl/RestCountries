package com.aditya.RestCountries;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.aditya.RestCountries.exceptions.CustomErrorHandler;


@SpringBootApplication
public class RestCountriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestCountriesApplication.class, args);
	}
    
	
	@Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate
				= new RestTemplate();
		restTemplate = builder.errorHandler(new CustomErrorHandler()).build();
		
		return restTemplate;
	}
}
