package com.example.parcial_programacion.controllers;

import com.example.parcial_programacion.business.services.IHumanService;
import com.example.parcial_programacion.business.services.impl.HumanService;
import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.dtos.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/human")
public class HumanController {
    @Autowired
    private IHumanService humanService;

    //Endpoint para guardar la secuencia de adn y verificar si es mutante
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody HumanDto humanDto) {
        try {
            //Llamar al servicio para guardar la secuencia de adn y verificar si es mutante
            if(humanService.save(humanDto)){
                return ResponseEntity.ok("Es mutante");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante");
            }
        } catch (Exception e) {
            //Manejar las excepciones
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la persona: " + e.getMessage());
        }
    }

    //Endpoint para obtener las estadisticas de mutantes y humanos
    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<StatsDto> stats() {
        //Llamar al servicio para obtener las estadisticas
        return ResponseEntity.ok(humanService.stats());
    }


}
