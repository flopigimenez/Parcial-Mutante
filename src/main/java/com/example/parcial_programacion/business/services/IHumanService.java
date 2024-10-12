package com.example.parcial_programacion.business.services;

import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.dtos.StatsDto;

public interface IHumanService<E>{

    boolean save(HumanDto humanDto);
    StatsDto stats();

}
