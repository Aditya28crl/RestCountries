package com.aditya.RestCountries.service;

import java.util.List;
import java.util.Map;

import com.aditya.RestCountries.Model.Country;

import reactor.core.publisher.Mono;

public interface CountryService {
 
	     
	
         List<Country> getAllCountries();

	     List<Country> getCountriesByPopulationDensity();
	    
	     Country getAsianCountryWithMaxBorderingCountries();
	     
	     Mono<Country> getAsianCountryWithMaxBorderingCountriesUsingWebCLient();

	     
	    
	    
}
