# RestCountries
This project retrieves list of countries data from an external API and performs various operations such as sorting and filtering based on given criterias. 

1. To build the source code
    - run the following command on terminal
       - mvn clean install -DskipTests=true 
     - this command will create a jar by the name of RestCountries-0.0.1-SNAPSHOT in the target folder. This jar will be used by Dockerfile to create docker.
    
2. To build and run docker  
     - The root folder contains a Dockerfile and docker-compose.yml
      
      - to run the docker: 
         - docker-compose up --build
      
      - to stop the docker:
       	 -	docker-compose down
 3. This service has following Rest End Points:
     - to get all the countries from external API.
        - /countries/v1/api/all 
     - to get the population density of all countries in descending order.   
        - /countries/v1/api/population_density 
     - to get Country in Asia containing the most bordering countries of a different region   
        - /countries/v1/api/asia_max_bordering_different_region 
     - to get Country in Asia containing the most bordering countries of a different region using webclient.
        - /countries/v1/api/asia_max_bordering_different_region_web_client                  
