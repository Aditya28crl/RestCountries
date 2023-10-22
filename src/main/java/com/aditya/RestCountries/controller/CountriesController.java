package com.aditya.RestCountries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.RestCountries.Model.Country;
import com.aditya.RestCountries.service.CountryService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/countries/v1/api")
public class CountriesController {

    
	@Autowired
	private CountryService countryService;
	    
	
	
		@GetMapping("/all")
	    public ResponseEntity <List<Country> > getAllCountries() {
			
			List<Country>  countries = countryService.getAllCountries();
	    	return new ResponseEntity<>(countries, HttpStatus.OK);
	
	    }

	    @GetMapping("/population_density")
	    public ResponseEntity<List<Country> >getCountriesByPopulationDensity() {
	        
	    	List<Country> countries = countryService.getCountriesByPopulationDensity();
	    	return new ResponseEntity<>(countries, HttpStatus.OK);
	    	 
	    }

	    
	    @GetMapping("/asia_max_bordering_different_region")
	    public ResponseEntity<Country> getAsianCountryWithMaxBorderingCountries() {
	        Country country = countryService.getAsianCountryWithMaxBorderingCountries();
	    	return new ResponseEntity<>(country, HttpStatus.OK);
 
	    }
	    
	    
	    @GetMapping("/asia_max_bordering_different_region_web_client")
	    public Mono<Country> getAsianCountryWithMaxBorderingCountriesUsingWebClient() {
	        
	    	try {
	            return countryService.getAsianCountryWithMaxBorderingCountriesUsingWebCLient();
	        }catch (Exception e){
	            e.printStackTrace();
	            return Mono.error(new Exception("server error"));
	            
	        }
	        
	    }
	    
	    
	    
	    
	    
	    
	    

	   

	
	
	
	
	
}
