package com.example.parcial_programacion.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

//Entidad de Human
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> dna;

    private String strDna;

    @Column(name = "is_mutant")
    private boolean ismutant;

    //Método para convertir la lista de adn a un string
    public void SetStrDna(){
        this.strDna = String.join("-", this.dna);
    }

}

