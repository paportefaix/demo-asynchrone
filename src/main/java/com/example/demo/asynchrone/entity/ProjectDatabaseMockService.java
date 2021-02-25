package com.example.demo.asynchrone.entity;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProjectDatabaseMockService {

    @SneakyThrows
    public CompletableFuture<List<ProjectEntity>> getAll() {
        Thread.sleep(50);
        return CompletableFuture.completedFuture(Arrays.asList(new ProjectEntity(1, "Awsome Project", 1, Arrays.asList(1, 2, 3), 1000000),
                                                               new ProjectEntity(2, "IA Project", 2, Arrays.asList(4, 5, 6), 10000),
                                                               new ProjectEntity(3, "Project Mahanttan", 3, Arrays.asList(1, 3), 10000000),
                                                               new ProjectEntity(4, "Hyperloop", 1, Collections.singletonList(7), 1000),
                                                               new ProjectEntity(5, "Electric bike", 4, Arrays.asList(2, 5, 8), 1000000),
                                                               new ProjectEntity(6, "Natural air", 3, Arrays.asList(2, 7), 1000000),
                                                               new ProjectEntity(7, "Mama kitchen", 5, Arrays.asList(1, 6), 1000000),
                                                               new ProjectEntity(8, "Crappy Project", 1, Arrays.asList(1, 2, 3, 4, 5, 6), 10000000)));
    }
}
