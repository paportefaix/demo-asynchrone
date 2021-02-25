package com.example.demo.asynchrone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ProjectEntity {
    private Integer       id;
    private String        name;
    private Integer       clientId;
    private List<Integer> agents;
    private Integer       budget;
}
