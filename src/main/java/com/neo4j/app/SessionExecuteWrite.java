package com.neo4j.app;

import java.util.Map;

import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.TransactionContext;

public class SessionExecuteWrite {
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

        // Create session and execute
        String name = "Martin";
        int age = 67;
        try (var session = driver.session()) {
            var count = session.executeWrite(tx -> createPerson(tx, name, age));
            System.out.println(
                String.format("%s nodes added.", count)
            );
        }

        // Close the connection
        driver.close();

    }

    public static int createPerson(TransactionContext tx, String name, int age) {
        var result = tx.run("""
            CREATE (p:Person {name: $name, age: $age})
            RETURN p
            """, Map.of("name", name, "age", age));
        return result.list().size();
    }
}
