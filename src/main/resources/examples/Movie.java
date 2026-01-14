package com.neo4j.app;

import java.util.List;

public record Movie(String movieId,
                    String title,
                    List<Person> actors) {

}
