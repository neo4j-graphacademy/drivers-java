package com.neo4j.app;

import java.util.Map;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import com.neo4j.app.Movie;
import com.neo4j.app.Person;

public class ObjectMapping {
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

        // Execute a Cypher query to find a person
        final String personCypher = """
            MATCH (person:Person {name: $name})
            RETURN person
            """;
        final String name = "Tom Hanks";

        var person = driver.executableQuery(personCypher)
            .withParameters(Map.of("name", name))
            .execute()
            .records()
            .stream()
            .map(record -> record.get("person").as(Person.class))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Person not found"));

        System.out.println(person);

        // Execute a Cypher query to find 1 movie and its actors
        final String movieCypher = """
            MATCH (movie:Movie)
            LIMIT 1
            RETURN movie {
                .*,
                actors: COLLECT {
                    MATCH (actor:Person)-[r:ACTED_IN]->(movie)
                    RETURN actor
                }
            }
            """;

        var movie = driver.executableQuery(movieCypher)
            .execute()
            .records()
            .stream()
            .map(record -> record.get("movie").as(Movie.class))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No movie found"));

        System.out.println(movie);

        // Close the connection
        driver.close();
    }
}
