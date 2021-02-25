package com.example.demo.asynchrone.controller;

import com.example.demo.asynchrone.dto.AgentDto;
import com.example.demo.asynchrone.dto.AgentMockWebService;
import com.example.demo.asynchrone.dto.ClientDto;
import com.example.demo.asynchrone.dto.ClientMockWebService;
import com.example.demo.asynchrone.dto.ProjectDto;
import com.example.demo.asynchrone.dto.ProjectV2Dto;
import com.example.demo.asynchrone.entity.ProjectDatabaseMockService;
import com.example.demo.asynchrone.entity.ProjectEntity;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class ProjectController {

    private final ProjectDatabaseMockService database;
    private final ClientMockWebService       clientMockWebService;
    private final AgentMockWebService        agentMockWebService;

    public ProjectController(ProjectDatabaseMockService database, ClientMockWebService clientMockWebService, AgentMockWebService userMockWebService) {
        this.database             = database;
        this.clientMockWebService = clientMockWebService;
        this.agentMockWebService  = userMockWebService;
    }

    @SneakyThrows
    @Async
    @GetMapping(value = "/projects-async")
    public CompletableFuture<List<ProjectDto>> getProjets() {

        // join() make blocking call, but we need to retrieve datas from project
        final List<ProjectEntity>                projectEntities = database.getAll().join();
        final CompletableFuture<List<ClientDto>> clients         = clientMockWebService.findAllIn(projectEntities.stream().map(ProjectEntity::getClientId).collect(Collectors.toList()));
        final CompletableFuture<List<AgentDto>>  agents          = agentMockWebService.findAllIn(projectEntities.stream().map(ProjectEntity::getAgents).flatMap(Collection::stream).collect(Collectors.toList()));

        return CompletableFuture
                // wait for both
                .allOf(clients, agents).thenApply(unused -> {
                    // retrieve result with join()
                    final List<ClientDto> clientDtos = clients.join();
                    final List<AgentDto>  userDtos   = agents.join();
                    return projectEntities.stream().map(projectEntity -> createProjectDto(projectEntity, clientDtos, userDtos)).collect(Collectors.toList());
                });


    }

    private ProjectDto createProjectDto(ProjectEntity projectEntity, List<ClientDto> clientDtos, List<AgentDto> userDtos) {
        final ClientDto clientDto = clientDtos
                .stream()
                .filter(client -> client.getId().equals(projectEntity.getClientId()))
                .findFirst()
                .orElse(new ClientDto());

        final List<AgentDto> agents = userDtos
                .stream()
                .filter(userDto -> projectEntity.getAgents().contains(userDto.getId()))
                .collect(Collectors.toList());

        return new ProjectDto(projectEntity.getId(), projectEntity.getName(), clientDto.getAdresse(), clientDto.getAdresse(), agents);
    }

    @SneakyThrows
    @Async
    @GetMapping(value = "/projects-async-v2")
    public CompletableFuture<List<ProjectDto>> getProjetsV2() {

        List<ProjectEntity> entities   = new ArrayList<>();
        List<ClientDto>     clientDtos = new ArrayList<>();
        List<AgentDto>      agentDtos  = new ArrayList<>();
        final CompletableFuture<List<ProjectEntity>> futureProjets = database.getAll().thenApply(projectEntities -> {
            entities.addAll(projectEntities);
            return projectEntities;
        });

        final CompletableFuture<Void> futureClients = futureProjets
                .thenApply(projectEntities -> projectEntities.stream().map(ProjectEntity::getClientId).collect(Collectors.toList()))
                .thenCompose(clientMockWebService::findAllIn)
                .thenAccept(clientDtos::addAll);

        final CompletableFuture<Void> futureAgents = futureProjets
                .thenApply(projectEntities -> projectEntities.stream().map(ProjectEntity::getAgents).flatMap(Collection::stream).collect(Collectors.toList()))
                .thenCompose(agentMockWebService::findAllIn)
                .thenAccept(agentDtos::addAll);

        return CompletableFuture
                .allOf(futureClients, futureAgents)
                .thenApply(unused -> entities
                                   .stream()
                                   .map(projectEntity -> createProjectDto(projectEntity, clientDtos, agentDtos))
                                   .collect(Collectors.toList())
                          );
    }

    @SneakyThrows
    @Async
    @GetMapping(value = "/projects-async-v3")
    public CompletableFuture<List<ProjectV2Dto>> getProjetsV3() {

        List<ProjectV2Dto> projectV2Dtos = new ArrayList<>();

        final CompletableFuture<Void> futureProjets = database.getAll()
                .thenAccept(projectEntities -> projectV2Dtos.addAll(projectEntities.stream().map(projectEntity ->
                                                                          new ProjectV2Dto().setId(projectEntity.getId())
                                                                                  .setName(projectEntity.getName())
                                                                                  .setAgentIds(projectEntity.getAgents())
                                                                                  .setCliendId(projectEntity.getClientId()))
                                             .collect(Collectors.toList())));

        final CompletableFuture<Void> futureClients = futureProjets
                .thenApply(unused -> projectV2Dtos.stream().map(ProjectV2Dto::getCliendId).collect(Collectors.toList()))
                .thenCompose(clientMockWebService::findAllIn)
                .thenAccept(clientDtos -> projectV2Dtos.forEach(projectV2Dto -> {
                    final ClientDto clientDto = clientDtos
                            .stream()
                            .filter(client -> client.getId().equals(projectV2Dto.getCliendId()))
                            .findFirst()
                            .orElse(new ClientDto());
                    projectV2Dto.setClientAdresse(clientDto.getAdresse());
                    projectV2Dto.setClientName(clientDto.getName());
                }));

        final CompletableFuture<Void> futureAgents = futureProjets
                .thenApply(unused -> projectV2Dtos.stream().map(ProjectV2Dto::getAgentIds).flatMap(Collection::stream).collect(Collectors.toList()))
                .thenCompose(agentMockWebService::findAllIn)
                .thenAccept(agentDtos -> projectV2Dtos.forEach(projectV2Dto -> {
                    final List<AgentDto> agents = agentDtos
                            .stream()
                            .filter(agentDto -> projectV2Dto.getAgentIds().contains(agentDto.getId()))
                            .collect(Collectors.toList());
                    projectV2Dto.setAgents(agents);
                }));

        return CompletableFuture
                .allOf(futureClients, futureAgents)
                .thenApply(unused -> projectV2Dtos);
    }
}
