package com.example.demo.asynchrone.dto;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AgentMockWebService {

    private static final List<AgentDto> agents = Arrays.asList(new AgentDto(1, "Name 1", "Surname 1"),
                                                               new AgentDto(2, "Name 2", "Surname 2"),
                                                               new AgentDto(3, "Name 3", "Surname 3"),
                                                               new AgentDto(4, "Name 4", "Surname 4"),
                                                               new AgentDto(5, "Name 5", "Surname 5"),
                                                               new AgentDto(6, "Name 6", "Surname 6"),
                                                               new AgentDto(7, "Name 7", "Surname 7"),
                                                               new AgentDto(8, "Name 8", "Surname 8"),
                                                               new AgentDto(9, "Name 9", "Surname 9"),
                                                               new AgentDto(10, "Name 10", "Surname 10"));

    @SneakyThrows
    public CompletableFuture<List<AgentDto>> findAllIn(final List<Integer> ids) {
        Thread.sleep(150);
        return CompletableFuture.completedFuture(agents.stream().filter(userDto -> ids.contains(userDto.getId())).collect(Collectors.toList()));
    }
}
