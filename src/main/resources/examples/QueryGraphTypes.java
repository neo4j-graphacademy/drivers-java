package com.neo4j.app;

import java.util.Map;

import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.types.Path;

public class QueryGraphTypes {
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
            MATCH path = (person:Person)-[actedIn:ACTED_IN]->(movie:Movie {title: $title})
            RETURN path, person, actedIn, movie
            """;
        final String title = "Toy Story";

        var result = driver.executableQuery(cypher)
            .withParameters(Map.of("title", title))
            .execute();

        // Parse the graph types
        var records = result.records();
        records.forEach(r -> {
            Node node = r.get("person").asNode();
            Relationship actedIn = r.get("actedIn").asRelationship();
            Path path = r.get("path").asPath();

            System.out.println(
                String.format("Element ID %s\n Labels %s\n Name %s", 
                    node.elementId(), 
                    node.labels(), 
                    node.get("name")
                )
            );

            System.out.println(
                String.format("Element ID %s\n Type %s\n Role %s", 
                    actedIn.elementId(), 
                    actedIn.type(), 
                    actedIn.get("role")
                )
            );

            System.out.println(
                String.format("Start Node ID %s\n End Node ID %s\n Length %s", 
                    path.start().elementId(), 
                    path.end().elementId(), 
                    path.length()
                )
            );
        });


        // Close the connection
        driver.close();

    }
}
