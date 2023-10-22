package com.aditya.RestCountries.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.aditya.RestCountries.Model.Country;

@SpringBootTest
public class CountryServiceImplTest {

	    @InjectMocks
	    private CountryServiceImpl countryService;

	    @Mock
	    private WebClient webClient;

	    @Mock
	    private RestTemplate restTemplate;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
      
	    
	    String URL = "https://restcountries.com/v3/all";
	    
	    @Test
	    public void testGetAllCountries() {
	        
	    	Country[] mockCountries = {new Country(), new Country()};
	        when(restTemplate.getForObject(URL,Country[].class)).thenReturn(mockCountries);

	        List<Country> countries = countryService.getAllCountries();

	        assertEquals(2, countries.size());
	    }

	    @Test
	    public void testGetCountriesByPopulationDensity() {
	        
	    	Country country1 = new Country();
	        country1.setArea(100.0);
	        country1.setPopulation(1000);
	        Country country2 = new Country();
	        country2.setArea(200.0);
	        country2.setPopulation(1500);

	        List<Country> mockCountries = Arrays.asList(country1, country2);

	        when(restTemplate.getForObject(URL,Country[].class)).thenReturn(mockCountries.toArray(new Country[0]));

	        List<Country> countries = countryService.getCountriesByPopulationDensity();

	        assertEquals(2, countries.size());
	        assertEquals(country1.getPopulation()/country1.getArea(),countries.get(0).getPopulationDensity() );
	        assertEquals(country2.getPopulation()/country2.getArea(),countries.get(1).getPopulationDensity() );
            
	        assertTrue(countries.get(0).getPopulationDensity()> countries.get(1).getPopulationDensity() );
	        assertEquals(country1, countries.get(0));
	        assertEquals(country2, countries.get(1));
	    }

	    @Test
	    public void testGetAsianCountryWithMaxBorderingCountriesDifferentRegion() {
	       
	    	Country country1 = new Country();
	        country1.setCca3("TUR");
	        country1.setRegion("Asia");
	        country1.setBorders(Arrays.asList("ARM", "BGR"));

	        Country country2 = new Country();
	        country2.setCca3("ARM");
	        country2.setRegion("Europe");
	        country2.setBorders(Arrays.asList("RUS","IND"));

	        Country country3 = new Country();
	        country3.setCca3("BGR");
	        country3.setRegion("AFRICA");
	        country3.setBorders(Arrays.asList("ABC","XYZ"));
	        
	        Country country4 = new Country();
	        country4.setCca3("CHN");
	        country4.setRegion("Asia");
	        country4.setBorders(Arrays.asList("IND", "RUS"));
	        
	        Country country5 = new Country();
	        country5.setCca3("IND");
	        country5.setRegion("Asia");
	        country5.setBorders(Arrays.asList("CHN", "RUS"));
	        
	        Country country6 = new Country();
	        country6.setCca3("RUS");
	        country6.setRegion("Asia");
	        country6.setBorders(Arrays.asList("CHN", "IND"));

	        when(restTemplate.getForObject(URL,Country[].class))
	            .thenReturn(new Country[] {country1, country2, country3, country4, country5, country6});

	        Country asianCountry = countryService.getAsianCountryWithMaxBorderingCountries();

	        assertEquals(country1, asianCountry);
	    }
	
	
}
