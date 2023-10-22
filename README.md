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
                   
