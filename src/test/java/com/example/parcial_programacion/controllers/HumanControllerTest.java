package com.example.parcial_programacion.controllers;

import com.example.parcial_programacion.business.services.impl.HumanService;
import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.dtos.StatsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HumanController.class)
public class HumanControllerTest {
    @BeforeAll
    public static void BeforeAll(){
        System.out.println("BeforeAll");
    }

    @AfterAll
    public static void AfterAll(){
        System.out.println("AfterAll");
    }

    @BeforeEach
    public  void BeforeEach(){
        System.out.println("BeforeEach");
    }

    @AfterEach
    public  void AfterEach(){
        System.out.println("AfterEach");
    }

    @MockBean
    private HumanService humanService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSave_isMutant() throws Exception{
        List<String> dna = new ArrayList<>();
        dna.add("ACTCTT");
        dna.add("ACTTTT");
        dna.add("AGCTGG");
        dna.add("ACTGGT");
        dna.add("AGGCGT");
        dna.add("AAAAAA");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);

        when(humanService.save(any(HumanDto.class))).thenReturn(true);

        mockMvc.perform(post("/human/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(humanDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Es mutante"));

    }

    @Test
    public void testSave_notMutant() throws Exception {
        List<String> dna = new ArrayList<>();
        dna.add("ACTCTT");
        dna.add("CCTATT");
        dna.add("CGCTGG");
        dna.add("ACTGGT");
        dna.add("AGGCGT");
        dna.add("ACGAAT");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);

        when(humanService.save(any(HumanDto.class))).thenReturn(false);

        mockMvc.perform(post("/human/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(humanDto)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("No es mutante"));

    }

    @Test
    public void testSave_shouldThrowException() throws Exception {
        HumanDto humanDto = new HumanDto();
        humanDto.setDna(new ArrayList<>());

        when(humanService.save(any(HumanDto.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/human/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(humanDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al guardar la persona: null"));
    }

    @Test
    public void testStats() throws Exception{
        StatsDto statsDto = StatsDto.builder()
                .countMutantDna(5L)
                .countHumanDna(10L)
                .ratio(0.5)
                .build();

        when(humanService.stats()).thenReturn(statsDto);

        mockMvc.perform(get("/human/stats"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(statsDto)));

    }

}
