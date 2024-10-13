package com.example.parcial_programacion.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

//Dto de Human
public class HumanDto {
    private String name;
    private List<String> dna;
}
