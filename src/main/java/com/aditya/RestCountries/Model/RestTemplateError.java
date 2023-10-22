package com.aditya.RestCountries.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestTemplateError {
	private String timestamp;
	private String status;
	private String error;
	private String path;
	
	
	

}