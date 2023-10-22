package com.aditya.RestCountries.exceptions;

import org.springframework.http.HttpStatus;

public class RestServiceException extends RuntimeException {

	 
	private static final long serialVersionUID = 1L;
	
	private String serviceName;
	private HttpStatus statusCode;
	private String error;
	public RestServiceException(String serviceName, HttpStatus statusCode, String error) {
		super();
		this.serviceName = serviceName;
		this.statusCode = statusCode;
		this.error = error;
	}
	public String getServiceName() {
		return serviceName;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public String getError() {
		return error;
	}

	

}