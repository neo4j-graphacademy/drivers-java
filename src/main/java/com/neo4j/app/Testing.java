package com.neo4j.app;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.neo4j.driver.summary.ResultSummary;
// Import Neo4j
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Driver;
import org.neo4j.driver.QueryConfig;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;

import org.neo4j.driver.RoutingControl;

import org.neo4j.driver.TransactionContext;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.types.Path;

import java.time.ZonedDateTime;
import java.time.ZoneId;

import java.time.Duration;
import java.time.LocalDateTime;

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


        // try (var session = driver.session()) {
        //     var result = session.executeWrite(
        //         tx -> getCheapestFlights(
        //             tx, 
        //             "2024-01-01", 
        //             "LAX",
        //             "SFO"
        //             )
        //         );
        //     var records = result.list();
        // }

        // String dtstring="2024-05-15T14:30:00+02:00";
        // var datetime = ZonedDateTime.of(2024, 05, 15, 14, 30, 00, 0, ZoneId.of("+02:00"));

        // var result = driver.executableQuery("""
        //     CREATE (e:Event {
        //       startsAt: $datetime,              // <1>
        //       createdAt: datetime($dtstring),   // <2>
        //       updatedAt: datetime()             // <3>
        //       })
        //       RETURN e.startsAt AS startsAt,
        //       e.createdAt AS createdAt,
        //       e.updatedAt AS updatedAt;
        //     """)
        //     .withParameters(Map.of("datetime", datetime, "dtstring", dtstring))
        //     .execute();

        // var event = result.records().get(0);
        // var startsAt = event.get("startsAt").asZonedDateTime();
        // var createdAt = event.get("createdAt").asZonedDateTime();
        // var updatedAt = event.get("updatedAt").asZonedDateTime();
        // System.out.println(startsAt);  // 2024-05-15T14:30+02:00
        // System.out.println(createdAt); // 2024-05-15T14:30+02:00
        // System.out.println(updatedAt); // today's date and time with +00:00 timezone

        // var result = driver.executableQuery("""
        //         RETURN date() as date, time() as time, 
        //             datetime() as datetime, 
        //             toString(datetime()) as asString;
        //         """)
        //         .execute();

        // // Loop through results and do something with them
        // var records = result.records();
        // records.forEach(r -> {
        //     System.out.println(r.get("date"));      // neo4j.time.Date
        //     System.out.println(r.get("time"));      // neo4j.time.Time
        //     System.out.println(r.get("datetime"));  // neo4j.time.DateTime
        //     System.out.println(r.get("asString"));  // String
        // });

        // // Create variables with duration and duration calculation
        // var startsAt = LocalDateTime.now();
        // var eventLength = Duration.ofHours(1).plusMinutes(30);
        // var endsAt = startsAt.plus(eventLength);

        // var result = driver.executableQuery("""
        //     CREATE (e:Event {
        //       startsAt: $startsAt, 
        //       endsAt: $endsAt,
        //       duration: $eventLength, // <1>
        //       interval: duration("PT1H30M") // <2>
        //     })
        //     RETURN e
        //     """)
        //     .withParameters(Map.of(
        //         "startsAt", startsAt,
        //         "endsAt", endsAt,
        //         "eventLength", eventLength
        //     ))
        //     .execute();

        // // Output results
        // var event = result.records().get(0).get("e").asNode();
        // System.out.println(event.get("startsAt")); // current datetime
        // System.out.println(event.get("endsAt"));   // current datetime + 1h 30m
        // System.out.println(event.get("duration")); // P0M0DT5400S (1h 30m in seconds)
        // System.out.println(event.get("interval")); // P0M0DT5400S (1h 30m in seconds)

        // var result = driver.executableQuery("RETURN point({x: 1.23, y: 4.56, z: 7.89}) AS point")
        //         .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
        //         .execute();
        // var point = result.records().get(0).get("point");
        // System.out.println(point);  // Point{srid=9157, x=2.3, y=4.5, z=2.0}
        // System.out.println(point.asPoint().x() + ", " + point.asPoint().y() + ", " + point.asPoint().z());  // 2.3

        // Double longitude = 55.296233;
        // Double latitude = 25.276987;
        // Double height = 828.0;

        // var location3d = Values.point(
        //     4979, 
        //     longitude, latitude, height
        // );


        // var location2d = Values.point(4326, -0.118092, 51.509865);
        // System.out.println(location2d.asPoint().x() + ", " + 
        //                     location2d.asPoint().y() + ", " + 
        //                     location2d.asPoint().srid());
        //                     // -0.118092, 51.509865, 4326

        // // var location3d = Values.point(4979, -0.086500, 51.504501, 310);
        // System.out.println(location3d.asPoint().x() + ", " + 
        //                     location3d.asPoint().y() + ", " + 
        //                     location3d.asPoint().z() + ", " + 
        //                     location3d.asPoint().srid());
                            // -0.0865, 51.504501, 310.0, 4979

        // var result = driver.executableQuery("""
        //     RETURN point({
        //         latitude: 51.5,
        //         longitude: -0.118,
        //         height: 100
        //     }) AS point
        // """)
        // .execute();

        // var point = result.records().get(0).get("point");
        // var longitude = point.asPoint().x();
        // var latitude = point.asPoint().y();
        // var height = point.asPoint().z();
        // var srid = point.asPoint().srid();
        // System.out.println(longitude + ", " +
        //                     latitude + ", " + 
        //                     height + ", " +
        //                     srid); // -0.118, 51.5, 100.0, 4979
        // System.out.println(point.asPoint()); // Point{srid=4979, x=-0.118, y=51.5, z=100.0}

        // // Create two points
        // var point1 = Values.point(7203, 1.23, 4.56);
        // var point2 = Values.point(7203, 2.34, 5.67);

        // var result = driver.executableQuery("""
        //         RETURN point.distance($p1, $p2) AS distance
        //         """)
        //         .withParameters(
        //             Map.of("p1", point1, "p2", point2))
        //         .execute();

        // var distance = result.records().get(0).get("distance").asDouble();
        // System.out.println(distance);  // 1.5697770542341356

        // try (var session = driver.session()) {
        //     // Run a Cypher statement
        //     var result = session.run("MATCH (n) RETURN n LIMIT 10");
        //     result.forEachRemaining(record -> {
        //         System.out.println(record.get("n").asNode().asMap());
        //     });
        // } catch (Neo4jException e) {
        //     e.code(); // Outputs the error code
        //     e.getMessage(); // Outputs the error message
        //     e.gqlStatus(); // Outputs the GQL status
        //     e.printStackTrace(); // Outputs full stack trace
        // }

        // var name = "Test Name";
        // var email = "test@test.com";
        
        // try (var session = driver.session()) {
        //     var result = session.run("""
        //         CREATE (u:User {name: $name, email: $email})
        //         RETURN u;
        //     """, 
        //     Values.parameters("name", name, "email", email));

        // } catch (Neo4jException e) {
        //     e.printStackTrace(); 
        //     // org.neo4j.driver.exceptions.ClientException:
        //         // Node(5) already exists with label `User` 
        //         // and property `email` = 'test@test.com'
        // }

        var result = createUser(driver, "Test User", "email: test_user@example.com");

        System.out.println(result);

        driver.close();

    }

    public static String createUser(Driver driver, String name, String email) {
        try (var session = driver.session()) {
            session.executeWrite(
                tx -> addUser(
                    tx,
                    "Test User",
                    "test_user@example.com"
                    )
                );

            return "{\"success\": true, \"message\": \"User created successfully\"}";

        } catch (ClientException e) {

            return "{\"success\": false, \"message\": \"User already exists\"}";

        }
    }

    public static ResultSummary addUser(TransactionContext tx, String name, String email) {

        var result = tx.run("""
        CREATE (u:User {name: $name, email: $email}) 
        RETURN u
        """, Map.of("name", name, "email", email));

        return result.consume();
        
    }

    // public static ResultSummary getAnswer(TransactionContext tx, String answer) {
    //     var result = tx.run("RETURN $answer AS answer", Map.of("answer", answer));
    //     return result.consume();
    // }

    // public static List<Record> getCheapestFlights(
    //     TransactionContext tx, 
    //     String date, 
    //     String origin, 
    //     String destination) {

    //     var result = tx.run("""
    //         MATCH (origin:Airport)<-[:ORIGIN]-(f:Flight)-[:DESTINATION]->(destination:Airport),
    //             (f)-[:OPERATED_BY]->(operator:Airline)
    //         WHERE origin.name = $origin AND destination.name = $destination AND f.date = $date
    //         RETURN f.price AS price, operator.name AS operator
    //         """, Map.of("date", date, "origin", origin, "destination", destination));

    //     return result.list();
    // }

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
