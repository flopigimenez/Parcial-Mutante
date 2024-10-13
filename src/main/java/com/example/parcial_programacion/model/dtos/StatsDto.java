package com.example.parcial_programacion.model.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

//Dto de Stats -- Estadísticas de validación de adn
public class StatsDto {
    private Long countMutantDna;
    private Long countHumanDna;
    private double ratio;
}
