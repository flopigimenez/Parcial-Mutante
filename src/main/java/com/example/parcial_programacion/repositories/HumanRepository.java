package com.example.parcial_programacion.repositories;

import com.example.parcial_programacion.model.entities.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Long> {
    boolean existsByStrDna(String strDna);
    long countByIsmutant(boolean ismutant);
}
