package com.neo4j.app;
import java.io.IOException;
import java.io.InputStream;

public class AppProperties {

    public static class ApplicationPropertiesNotFound extends RuntimeException {
        public ApplicationPropertiesNotFound(String message) {
            super(message);
        }
    }

    public static void loadProperties() {       
        try (InputStream input = AppProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new ApplicationPropertiesNotFound("application.properties file not found");
            }
            System.getProperties().load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading application.properties", ex);
        }
    }
}