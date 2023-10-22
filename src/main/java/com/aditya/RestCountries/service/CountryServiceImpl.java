package com.aditya.RestCountries.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.aditya.RestCountries.Model.Country;
import com.aditya.RestCountries.exceptions.CountryDataNotFoundException;
import com.aditya.RestCountries.exceptions.CustomException;

import reactor.core.publisher.Mono;


@Service
public class CountryServiceImpl implements CountryService {

	
    private static final Logger logger = LogManager.getLogger(CountryService.class);

	@Autowired
    WebClient webClient;

	
    @Autowired
    RestTemplate restTemplate;
	
    @Value("${external.api.url}")
	private String RESTCOUNTRIES_API;
        
        
	 @Override
		public List<Country> getAllCountries() {
		 
		     
		     Country[] countries = restTemplate.getForObject(RESTCOUNTRIES_API, Country[].class);
			 
		     if (countries == null || countries.length == 0) {
		            throw new CountryDataNotFoundException("No countries found");
		        }
		     
			 return Arrays.asList(countries);
		
		
		}
	  
		@Override
		public List<Country> getCountriesByPopulationDensity() {
							
				Country[] countriesArray = restTemplate.getForObject(RESTCOUNTRIES_API, Country[].class);		        
				
				 if (countriesArray == null || countriesArray.length == 0) {
			            throw new CountryDataNotFoundException("No countries found");
			        }
				
	             
				List<Country> countries = new ArrayList<>();
				countries = Arrays.asList(countriesArray);
				
		        return countries.stream()
		                .map(country -> {
		                    
		                	
		                	if(country.getArea()==0)
		                	{
			                    country.setPopulationDensity(0);
		                	}
		                	else{	
				                    
		                		double density = country.getPopulation() / country.getArea();
				                country.setPopulationDensity(density);
			                    
		                	    }
		                	return country;
		                })
		                .sorted(Comparator.comparingDouble(country -> -(Double) country.getPopulationDensity()))
		                .collect(Collectors.toList());
	            
	       
		}

		@Override
		public Country getAsianCountryWithMaxBorderingCountries() {
			
			
			Country[] countriesArray = restTemplate.getForObject(RESTCOUNTRIES_API, Country[].class);		        
	        
			 if (countriesArray == null || countriesArray.length == 0) {
		            throw new CountryDataNotFoundException("No countries found");
		        }
	        
			 List<Country> countries = new ArrayList<>();
				countries = Arrays.asList(countriesArray);
			 
			 
	        List<Country> asianCountries = countries.stream()
	                .filter(country -> "Asia".equalsIgnoreCase((String) country.getRegion()))
	                .collect(Collectors.toList());
            
	        int max = 0;
	        
	        Country asianCountryResult = new Country();
	        
	        for (Country asianCountry : asianCountries) {
	        	
	        	
	            List<String> neighborRegionsCode = asianCountry.getBorders();
	                 
	                 int localMax = 0;
	            
	            if(neighborRegionsCode!=null)     
	            for( String neighborCode : neighborRegionsCode)
	            {
	            	 
	            	 List<Country> uniqueCountry = new ArrayList<>();
	            	 uniqueCountry =  countries.stream()
	            			 .filter(c->neighborCode.equalsIgnoreCase(c.getCca3()))
	            			 .filter(c->!"Asia".equalsIgnoreCase(c.getRegion()))		 
	            	         .collect(Collectors.toList());
	            	 
	            	   if(uniqueCountry.size()>0)
	            	   {   
	  	            	 System.out.println(neighborCode + " " + uniqueCountry.get(0).getRegion());

	            		   localMax++;
	            	   }
	            	 	
	             }
	            
            	System.out.println("local max =" + localMax + "country name= " + asianCountry.getName());

	            if(localMax>max)
	            {
	            	max = localMax;
	            
	            	asianCountryResult= asianCountry;
	            	
	            	System.out.println(" max =" + "country name= " + asianCountry.getName());
	            }


	            
	        }

            return asianCountryResult;

		}

		  public List<Country> getAllCountriesUsingWebClient() {
			  List<Country> countries =  webClient.get()
		            .uri("all")
		            .retrieve()
		            .onStatus(HttpStatus::is4xxClientError, 
	                		ClientResponse-> Mono.error(new CustomException("client error",ClientResponse.statusCode().value())) )
	                .onStatus(HttpStatus::is5xxServerError, 
	                		ClientResponse-> Mono.error(new CustomException("Internal server error",ClientResponse.statusCode().value())))
		            .bodyToFlux(Country.class)
		            .doOnError(error -> {
	                    logger.error("Failed to retrieve country data.", error);
	                  
	                })
		            .collectList()
		            .block();
		       
		        
		        return countries;
		    }
		
	
		@Override
		public Mono<Country> getAsianCountryWithMaxBorderingCountriesUsingWebCLient() {
			  
			 List<Country> countries = getAllCountriesUsingWebClient();
			 
			 List<Country> asianCountries = countries.stream()
		                .filter(country -> "Asia".equalsIgnoreCase((String) country.getRegion()))
		                .collect(Collectors.toList());
			 
			 if(asianCountries.isEmpty())
			 {
				 throw new CountryDataNotFoundException("No Asian region country found");
			 }
	            
		        int max = 0;
		        
		        Country asianCountryResult = new Country();
		        
		        for ( Country asianCountry : asianCountries) {
		        	
		            List<String> neighborRegionsCode = asianCountry.getBorders();
		                 
		                 int localMax = 0;
		            
		            if(neighborRegionsCode!=null)     
		            for( String neighborCode : neighborRegionsCode)
		            {
		            	 
		            	 List<Country> uniqueCountry = new ArrayList<>();
		            	 uniqueCountry =  countries.stream()
		            			 .filter(c->neighborCode.equalsIgnoreCase(c.getCca3()))
		            			 .filter(c->!"Asia".equalsIgnoreCase(c.getRegion()))		 
		            	         .collect(Collectors.toList());
		            	 
		            	   if(uniqueCountry.size()>0)
		            	   {   
		  	            	 System.out.println(neighborCode + " " + uniqueCountry.get(0).getRegion());

		            		   localMax++;
		            	   }
		            	 	
		            }
		            
	            	System.out.println("local max =" + localMax + "country name= " + asianCountry.getCca3());

		            if(localMax>max)
		            {
		            	max = localMax;
		            
		            	asianCountryResult= asianCountry;
		            	
		            	System.out.println(" max =" + "country name= " + asianCountry.getCca3());
		            }
  
		        }

	            return Mono.just(asianCountryResult);
			
		          
		}

		
         
		/* if i am not using model class */
//		@Override
//		public List<Map<String, Object>> getCountriesByPopulationDensity() {
//			
//				String url = "https://restcountries.com/v3.1/all?fields=name,population,area";
//				
//				List<Map<String, Object>> countries = restTemplate.getForObject(url, List.class);
//		        
//				
//				if (countries.isEmpty()) {
//					 throw new CountryDataNotFoundException("No countries found");
//				}
//				
//	 
//		        return countries.stream()
//		                .filter(country -> country.containsKey("population") && country.containsKey("area"))
//		                .map(country -> {
//		                    String name = country.get("name").toString();
//		                    double population =   Double.valueOf(country.get("population").toString());
//		                    double area =  Double.valueOf(country.get("area").toString());
//		                    double density = population / area;
//		                    country.put("populationDensity", density);
//		                    return country;
//		                })
//		                .sorted(Comparator.comparingDouble(country -> -(Double) country.get("populationDensity")))
//		                .collect(Collectors.toList());
//	            
//	       
//		}
		
		
		
}
