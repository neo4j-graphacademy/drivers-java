package com.neo4j.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.neo4j.driver.summary.ResultSummary;
// Import Neo4j
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Result;

import org.neo4j.driver.RoutingControl;

import org.neo4j.driver.TransactionContext;

import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.types.Path;

public class Testing {
    public static void main(String[] args) {
        System.out.println("Using Neo4j with Java!");

        AppProperties.loadProperties();
    
        System.out.println(System.getProperty("NEO4J_URI"));
        
        // Connect to Neo4j database
        var driver = GraphDatabase.driver(
                System.getProperty("NEO4J_URI"), 
                AuthTokens.basic(
                    System.getProperty("NEO4J_USERNAME"), 
                    System.getProperty("NEO4J_PASSWORD"))
            );
    
        driver.verifyConnectivity();
        System.out.println("Connection established.");
        
        // var result = driver.executableQuery(
        //     "RETURN COUNT {()} AS count"
        //     ).execute();

        // var records = result.records();
        // var first = records.get(0); // Get the first record
        // System.out.println(first.get("count"));

        // try-with-resources
        // try (
        //     var driver = GraphDatabase.driver(
        //         System.getProperty("NEO4J_URI"), 
        //         AuthTokens.basic(
        //             System.getProperty("NEO4J_USERNAME"), 
        //             System.getProperty("NEO4J_PASSWORD"))
        //     )
        // ) {
        //     driver.verifyConnectivity();
        //     System.out.println("Connection established.");
            
        //     var result = driver.executableQuery("RETURN COUNT {()} AS count")
        //         .execute();

        //     var records = result.records();
        //     var first = records.get(0).get("count"); // Get the first record
        //     System.out.println(first);
        // } 

        // MATCH (p:Person {name: $name})-[r:ACTED_IN]->(m:Movie) 
        // RETURN m.title AS title, r.role AS role

        // final String cypher = """
        //     MATCH (p:Person {name: $name})-[r:ACTED_IN]->(m:Movie)
        //     RETURN m.title AS title, r.role AS role
        //     """;
        // final String name = "Tom Hanks";

        // var result = driver.executableQuery(cypher)
        //     .withParameters(Map.of("name", name))
        //     .withConfig(QueryConfig.builder()
        //                 .withRouting(RoutingControl.READ)
        //                 .build())
        //     .execute();        

        // var records = result.records();
        // var summary = result.summary();
        // var keys = result.keys();

        // System.out.println(records);
        // System.out.println(summary);
        // System.out.println(keys);

        // var records = result.records();
        // records.forEach(r -> {
        //         // System.out.println(
        //         //     String.format("Movie: %s, Role: %s", 
        //         //         r.get("title"), 
        //         //         r.get("role")
        //         //         )
        //         // );  
        //         System.out.println(r.get("title"));
        //         System.out.println(r.get("role"));
        //     });

        // var summary = result.summary();
        // System.out.printf("The query %s returned %d records in %d ms.%n",
        //     summary.query(), 
        //     records.size(),
        //     summary.resultAvailableAfter(TimeUnit.MILLISECONDS));

        // String name = "Martin O";
        // int age = 67;
        // try (var session = driver.session()) {
        //     var count = session.executeWrite(tx -> createPerson(tx, name, age));
        //     System.out.println(
        //         String.format("%s nodes added.", count)
        //     );
        // }

        // String fromAccount = "12345678";
        // String toAccount = "87654321";
        // double amount = 100;
        // try (var session = driver.session()) {
        //     session.executeWriteWithoutResult(tx -> transferFunds(tx, fromAccount, toAccount, amount));
        // }
        

        // String result = "Hello, World!";
        // try (var session = driver.session()) {
        //     ResultSummary summary = session.executeWrite(tx -> getAnswer(tx, result));
        //     System.out.println(
        //         String.format(
        //             "Results available after %d ms and consumed after %d ms",
        //             summary.resultAvailableAfter(TimeUnit.MILLISECONDS),
        //             summary.resultConsumedAfter(TimeUnit.MILLISECONDS)
        //         )
                
        //     );
        // }

        // final String cypher = """
        //     MATCH path = (person:Person)-[actedIn:ACTED_IN]->(movie:Movie {title: $title})
        //     RETURN path, person, actedIn, movie
        //     """;
        // final String title = "Toy Story";

        // var result = driver.executableQuery(cypher)
        //     .withParameters(Map.of("title", title))
        //     .execute();

        // var records = result.records();
        // records.forEach(r -> {
        //     Path path = r.get("path").asPath();
            
        //     System.out.println(path.nodes());
        //     System.out.println(path.relationships());

        //     System.out.println(path.start());
        //     System.out.println(path.end());
        //     System.out.println(path.length());

            // Node node = r.get("person").asNode();

            // System.out.println(node.elementId());
            // System.out.println(node.labels());
            // System.out.println(node.values());

            // System.out.println(node.get("name"));

            // Relationship actedIn = r.get("actedIn").asRelationship();
            
            // System.out.println(actedIn.elementId());
            // System.out.println(actedIn.type());
            // System.out.println(actedIn.values());

            // System.out.println(actedIn.get("role"));

            // System.out.println(actedIn.startNodeElementId());
            // System.out.println(actedIn.endNodeElementId());


        // });

        
        // var summary = result.summary();
        // var keys = result.keys();

        // System.out.println(records);
        // System.out.println(summary);
        // System.out.println(keys);


        try (var session = driver.session()) {
            var result = session.executeWrite(
                tx -> getCheapestFlights(
                    tx, 
                    "2024-01-01", 
                    "LAX",
                    "SFO"
                    )
                );
            var records = result.list();
        }


        driver.close();

    }

    public static ResultSummary getAnswer(TransactionContext tx, String answer) {
        var result = tx.run("RETURN $answer AS answer", Map.of("answer", answer));
        return result.consume();
    }

    public static Result getCheapestFlights(
        TransactionContext tx, 
        String date, 
        String origin, 
        String destination) {

        var result = tx.run("""
            MATCH (origin:Airport)<-[:ORIGIN]-(f:Flight)-[:DESTINATION]->(destination:Airport),
                (f)-[:OPERATED_BY]->(operator:Airline)
            WHERE origin.name = $origin AND destination.name = $destination AND f.date = $date
            RETURN f.price AS price, operator.name AS operator
            """, Map.of("date", date, "origin", origin, "destination", destination));

        return result;
    }

    // public static int createPerson(TransactionContext tx, String name, int age) {
    //     var result = tx.run("""
    //         CREATE (p:Person {name: $name, age: $age})
    //         RETURN p
    //         """, Map.of("name", name, "age", age));
    //     return result.list().size();
    // }

    // public static void transferFunds(TransactionContext tx, String fromAccount, String toAccount, double amount) {
    //     tx.run(
    //         "MATCH (a:Account {id: $from_}) SET a.balance = a.balance - $amount",
    //         Map.of("from_", fromAccount, "amount", amount)
    //     );

    //     tx.run(
    //         "MATCH (a:Account {id: $to_}) SET a.balance = a.balance + $amount",
    //         Map.of("to_", toAccount, "amount", amount)
    //     );
    
}
