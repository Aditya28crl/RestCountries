package com.aditya.RestCountries.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {
   
	private CountryName name;
    private double area;
    private long population;
    private String cca3;
    private double populationDensity;
    private String region;
    private List<String> borders;

    
}

