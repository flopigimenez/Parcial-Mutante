package com.example.parcial_programacion.business.mapper;

import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.entities.Human;
import jakarta.persistence.Cache;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = HumanMapper.class)
public interface HumanMapper {
    Human humanDtoToHuman (HumanDto humanDto);
    HumanDto humanToHumanDto (Human human);

    @AfterMapping
    default void SetStrDna (@MappingTarget Human human) {
        human.SetStrDna();
    }
}
