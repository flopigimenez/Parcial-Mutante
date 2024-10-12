package com.example.parcial_programacion.model.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatsDto {
    private Long countMutantDna;
    private Long countHumanDna;
    private double ratio;
}
