package com.example.parcial_programacion.business.mapper;

import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.entities.Human;
import jakarta.persistence.Cache;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

//Mapper para la entidad Human
@Mapper(componentModel = "spring", uses = HumanMapper.class)
public interface HumanMapper {
    //Mapear de humanDto a Human
    Human humanDtoToHuman (HumanDto humanDto);

    //Despues de mapear convertir lista de adn a string
    @AfterMapping
    default void SetStrDna (@MappingTarget Human human) {
        human.SetStrDna();
    }
}
