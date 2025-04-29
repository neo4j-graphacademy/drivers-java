package com.neo4j.app;

import java.util.Map;

import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;

public class ExecuteQuery {
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
        final String cypher = """
            MATCH (p:Person {name: $name})-[r:ACTED_IN]->(m:Movie)
            RETURN m.title AS title, r.role AS role
            """;
        final String name = "Tom Hanks";

        var result = driver.executableQuery(cypher)
            .withParameters(Map.of("name", name))
            .execute();

        // Parse the results
        var records = result.records();
        var summary = result.summary();
        var keys = result.keys();

        System.out.println(records);
        System.out.println(summary);
        System.out.println(keys);

        records.forEach(r -> {
            System.out.println(r.get("title"));
            System.out.println(r.get("role"));
        });

        // Close the connection
        driver.close();

    }
}
