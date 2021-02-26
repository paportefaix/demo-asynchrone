package com.example.demo.asynchrone.dto;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ClientMockWebService {

    private static final List<ClientDto> clients = Arrays.asList(new ClientDto(1, "Client 1", "Rue d'ici", "123"),
                                                                 new ClientDto(2, "Client 2", "Rue de là bas", "1325"),
                                                                 new ClientDto(3, "Client 3", "Avenue du code", "13466"),
                                                                 new ClientDto(4, "Client 4", "Impasse de la loose", "235123"),
                                                                 new ClientDto(5, "Client 5", "Rue du code", "28863"));

    @SneakyThrows
    public CompletableFuture<List<ClientDto>> findAllIn(final List<Integer> ids) {
        Thread.sleep(150);
        return CompletableFuture.completedFuture(clients.stream().filter(clientDto -> ids.contains(clientDto.getId())).collect(Collectors.toList()));
    }
}
