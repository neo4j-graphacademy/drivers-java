package com.neo4j.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

public class AppTest {

    public void loadProperties() {
        try {
            AppProperties.loadProperties();
        } catch (AppProperties.ApplicationPropertiesNotFound e) {
            fail("application.properties file not found", e);
        }
    }

    @Test
    public void shouldLoadProperties() {
        loadProperties();
        assertTrue(System.getProperty("NEO4J_URI") != null, "NEO4J_URI is null");
        assertTrue(System.getProperty("NEO4J_USERNAME") != null, "NEO4J_USERNAME is null");
        assertTrue(System.getProperty("NEO4J_PASSWORD") != null, "NEO4J_PASSWORD is null");
    }

    @Test
    public void shouldConnectToNeo4j() {
        loadProperties();

        try (
            var driver = GraphDatabase.driver(
                System.getProperty("NEO4J_URI"), 
                AuthTokens.basic(
                    System.getProperty("NEO4J_USERNAME"), 
                    System.getProperty("NEO4J_PASSWORD"))
            )
        ) {
            try {
                driver.verifyConnectivity();
            } catch (Exception e) {
                fail("Failed to connect to Neo4j, check application.properties", e);
            }
        }
        
    }
}
