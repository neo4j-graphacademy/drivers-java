package com.neo4j.app;

import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;

public class Connect {
    public static void main(String[] args) {
        AppProperties.loadProperties();

        // Create a new Neo4j driver instance
        var driver = GraphDatabase.driver(
                System.getProperty("NEO4J_URI"), // <1>
                AuthTokens.basic(
                    System.getProperty("NEO4J_USERNAME"), // <2>
                    System.getProperty("NEO4J_PASSWORD")) 
            );

        // Verify the connection 
        driver.verifyConnectivity();

        // Execute a Cypher query
        var result = driver.executableQuery(
            "RETURN COUNT {()} AS count"
            ).execute();

        // Parse the results
        var records = result.records();
        var first = records.get(0); // Get the first record
        System.out.println(first.get("count"));

        // Close the connection
        driver.close();

    }
}
