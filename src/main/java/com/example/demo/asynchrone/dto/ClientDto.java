package com.example.demo.asynchrone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Integer id;
    private String  name;
    private String  adresse;
    private String  phoneNumber;
}
