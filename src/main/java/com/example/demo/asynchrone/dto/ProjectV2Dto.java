package com.example.demo.asynchrone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProjectV2Dto {

    private Integer        id;
    private String         name;
    private String         clientName;
    private String         clientAdresse;
    private List<AgentDto> agents;

    @JsonIgnore
    private List<Integer>  agentIds;
    @JsonIgnore
    private Integer        cliendId;

}
