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

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody HumanDto humanDto) {
        try {
            if(humanService.save(humanDto)){
                return ResponseEntity.ok("Es mutante");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la persona: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<StatsDto> stats() {
        return ResponseEntity.ok(humanService.stats());
    }


}
