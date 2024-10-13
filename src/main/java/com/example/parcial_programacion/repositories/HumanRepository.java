package com.example.parcial_programacion.repositories;

import com.example.parcial_programacion.model.entities.Human;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositorio para la entidad Human
public interface HumanRepository extends JpaRepository<Human, Long> {
    //Método para validar si adn es existente
    boolean existsByStrDna(String strDna);

    //Método para obtener la cantidad de adn mutantes y no mutantes
    long countByIsmutant(boolean ismutant);
}
