package com.example.parcial_programacion.business.services.impl;

import com.example.parcial_programacion.business.mapper.HumanMapper;
import com.example.parcial_programacion.business.services.IHumanService;
import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.dtos.StatsDto;
import com.example.parcial_programacion.model.entities.Human;
import com.example.parcial_programacion.repositories.HumanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService implements IHumanService<Human> {
    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private HumanMapper humanMapper;

    @Override
    @Transactional
     public boolean save(HumanDto humanDto){
        Human human = humanMapper.humanDtoToHuman(humanDto);

        if (isValidDna(human).equals("empty")){
            throw new IllegalArgumentException("El ADN no puede ser nulo o vacío");
        }else if (isValidDna(human).equals("letter")){
            throw new IllegalArgumentException("Solo puede contener las letras A,T,C,G");
        }else if (isValidDna(human).equals("size")){
            throw new IllegalArgumentException("El adn debe tener una dimension de NxN");
        }else if (isValidDna(human).equals("exist")) {
            throw new IllegalArgumentException("El ADN no puede ser existente");
        }

        boolean ismutant = isMutant(humanDto);
        human.setIsmutant(ismutant);

        humanRepository.save(human);

        return ismutant;
     }

    @Override
    @Transactional
    public StatsDto stats() {
        Long countMutantDna = humanRepository.countByIsmutant(true);
        Long countHumanDna = humanRepository.countByIsmutant(false);
        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        StatsDto statsDto = StatsDto.builder()
                .countMutantDna(countMutantDna)
                .countHumanDna(countHumanDna)
                .ratio(ratio)
                .build();

        return statsDto;
    }

    public String isValidDna(Human human){
        List<String> dna = human.getDna();

        if (dna == null || dna.isEmpty()) {
            return "empty";
        }

        //Revisar letras y longitud
        for (int f = 0; f < dna.size(); f++) {
            if (dna.size() != dna.get(f).length()){
                return "size";
            }
            for (int c = 0; c < dna.get(f).length(); c++) {
                char letter = dna.get(f).toUpperCase().charAt(c);
                if (letter != 'A' && letter != 'C' && letter != 'G' && letter != 'T') {
                    return "letter";
                }
            }
        }

        if (humanRepository.existsByStrDna(human.getStrDna())) {
            return "exist";
        }

        return "";
    }

    public static boolean isMutant(HumanDto humanDto){
        List<String> dna = humanDto.getDna();
        int secuencias = 0;

        //horizontal y vertical
        String horizontal;
        for (int f = 0; f < dna.size(); f++){
            horizontal = dna.get(f).toUpperCase();
            secuencias += cantidadSecuencias(horizontal);
            String vertical = "";
            for (int c = 0; c < dna.get(f).length(); c++) {
                vertical += dna.get(c).toUpperCase().charAt(f);
            }
            secuencias += cantidadSecuencias(vertical);
        }

        //diagonales
        int cont = 0;
        int cont2;
        while (cont < dna.size()){
            String diagonalSup = "";
            String diagonalInf="";
            String diagonalInvSup = "";
            for (int f = 0; f < dna.size() - cont; f++){
                diagonalSup += dna.get(f).toUpperCase().charAt(f + cont);
                diagonalInf += dna.get(f + cont).toUpperCase().charAt(f);
                cont2 = dna.size()-cont-1;
                diagonalInvSup += dna.get(f).toUpperCase().charAt(cont2-f);
            }

            String diagonalInvInf = "";
            cont2 = cont;
            for (int f = dna.size()-1; f >= cont; f--) {
                diagonalInvInf += dna.get(f).toUpperCase().charAt(cont2);
                cont2 += 1;
            }

            secuencias += cantidadSecuencias(diagonalSup);
            secuencias += cantidadSecuencias(diagonalInvSup);
            if (cont > 0){
                secuencias += cantidadSecuencias(diagonalInf);
                secuencias += cantidadSecuencias(diagonalInvInf);
            }
            cont += 1;
        }

        return secuencias > 1;
    }

    public static int cantidadSecuencias(String cadena){
        if (cadena.contains("AAAA") || cadena.contains("CCCC") || cadena.contains("GGGG") || cadena.contains("TTTT")){
            return 1;
        }
        return 0;
    }
}