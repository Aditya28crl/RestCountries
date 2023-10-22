FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} restcountries.jar

ENTRYPOINT ["java", "-jar", "/restcountries.jar"]

EXPOSE 8080