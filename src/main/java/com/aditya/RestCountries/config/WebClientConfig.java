package com.aditya.RestCountries.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.aditya.RestCountries.service.CountryService;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private static final Logger logger = LogManager.getLogger(WebClientConfig.class);

	
    
    @Value("${external.base.url}")
   	private String base_url;
    
	    @Bean
	    public WebClient webclient() {

	        WebClient webClient = WebClient
	                .builder()
	                .baseUrl(base_url)
	                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	                .filter(logRequest())
	                .filter(logResponse())
	                .build();
	        return webClient;
	    }
	
	   
	 
	    private ExchangeFilterFunction logRequest() {
	 	   
			   return ExchangeFilterFunction.ofRequestProcessor(clientRequest->{
			       
				   logger.info("Request {} {}", clientRequest.method(), clientRequest.url());
				   return Mono.just(clientRequest);
			   
			   });
			   
		   }
	   
	   private ExchangeFilterFunction logResponse() {
	   
		   return ExchangeFilterFunction.ofResponseProcessor(clientResponse->{
		       
			   logger.info("response status code {}", clientResponse.statusCode());
			   return Mono.just(clientResponse);
		   
		   });
		   
	   }
	 
}
