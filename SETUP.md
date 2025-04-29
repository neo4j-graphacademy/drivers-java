# Setup

This repository accompanies the [Using Neo4j with Java](https://graphacademy.neo4j.com/courses/drivers-java) on [GraphAcademy](https://graphacademy.neo4j.com).

When the devcontainer is created, such as in a GitHub codespace, all the required software and packages will be installed.

Follow the [Setup Instructions in GraphAcademy](https://graphacademy.neo4j.com/courses/drivers-java/1-driver/0-setup/) to get started:

You will need to: 

- Create a new [`/src/main/resources/application.properties`](src/main/resources/application.properties) file and copy the [`example.properties`](src/main/resources/example.properties) file into it.
- Update the environment variables with the values in the [Setup Instructions](https://graphacademy.neo4j.com/courses/drivers-java/1-driver/0-setup/)
- Run the [`src/test/java/com/neo4j/app/AppTest.java`](src/test/java/com/neo4j/app/AppTest.java) test:

    ```bash
    ./mvnw test
    ```