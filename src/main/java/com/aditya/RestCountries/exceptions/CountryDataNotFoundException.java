package com.aditya.RestCountries.exceptions;


public class CountryDataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CountryDataNotFoundException(String message) {
        super(message);
    }
}