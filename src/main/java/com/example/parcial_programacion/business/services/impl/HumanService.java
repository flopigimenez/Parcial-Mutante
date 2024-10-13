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
        //Mapear de humanDto a human
        Human human = humanMapper.humanDtoToHuman(humanDto);

        //Validar adn
        if (isValidDna(human).equals("empty")){
            throw new IllegalArgumentException("El ADN no puede ser nulo o vacío");
        }else if (isValidDna(human).equals("letter")){
            throw new IllegalArgumentException("Solo puede contener las letras A,T,C,G");
        }else if (isValidDna(human).equals("size")){
            throw new IllegalArgumentException("El adn debe tener una dimension de NxN");
        }else if (isValidDna(human).equals("exist")) {
            throw new IllegalArgumentException("El ADN no puede ser existente");
        }

        //Llamar a un metodo que valida si el adn es mutante
        boolean ismutant = isMutant(humanDto);
        human.setIsmutant(ismutant);

        //Guardar el humano en la base de datos
        humanRepository.save(human);

        //Devolver si es mutante o no
        return ismutant;
     }

    @Override
    @Transactional
    public StatsDto stats() {
        //Llamar al metodo del repositorio para contar cantidad de adn mutantes y no mutantes
        Long countMutantDna = humanRepository.countByIsmutant(true);
        Long countHumanDna = humanRepository.countByIsmutant(false);
        //Calcular la relación de mutantes a humanos
        double ratio = (countHumanDna == 0) ? 0 : (double) countMutantDna / countHumanDna;

        //Guardar los datos en statsDto
        StatsDto statsDto = StatsDto.builder()
                .countMutantDna(countMutantDna)
                .countHumanDna(countHumanDna)
                .ratio(ratio)
                .build();

        //Devolver resultados de la estadistica
        return statsDto;
    }

    public String isValidDna(Human human){
        List<String> dna = human.getDna();

        //Validar si la lista de adn esta vacía
        if (dna == null || dna.isEmpty()) {
            return "empty";
        }

        //Revisar letras y longitud
        for (int f = 0; f < dna.size(); f++) {
            //Validar si la lista de adn es NxN
            if (dna.size() != dna.get(f).length()){
                return "size";
            }
            for (int c = 0; c < dna.get(f).length(); c++) {
                //Validar que las letras de la lista de adn sean 'A','C','G','T'
                char letter = dna.get(f).toUpperCase().charAt(c);
                if (letter != 'A' && letter != 'C' && letter != 'G' && letter != 'T') {
                    return "letter";
                }
            }
        }

        //Llamar a un método del repositorio para validar si el adn es existente
        if (humanRepository.existsByStrDna(human.getStrDna())) {
            return "exist";
        }

        return "";
    }

    public static boolean isMutant(HumanDto humanDto){
        List<String> dna = humanDto.getDna();
        //Contar las secuencias de cuatro letras iguales
        int secuencias = 0;

        //horizontal y vertical
        String horizontal;
        for (int f = 0; f < dna.size(); f++){
            //Guardar las letras de manera horizontal en un string
            horizontal = dna.get(f).toUpperCase();
            //Aumentar la cant de secuencias si hay una secuencia de cuatro letras iguales
            secuencias += cantidadSecuencias(horizontal);
            String vertical = "";
            for (int c = 0; c < dna.get(f).length(); c++) {
                //Guardar las letras de manera vertical en un string
                vertical += dna.get(c).toUpperCase().charAt(f);
            }
            //Aumentar la cant de secuencias si hay una secuencia de cuatro letras iguales
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
                //Guardar las letras de la diagonal superior en un string
                diagonalSup += dna.get(f).toUpperCase().charAt(f + cont);
                //Guardar las letras de la diagonal inferior en un string
                diagonalInf += dna.get(f + cont).toUpperCase().charAt(f);
                cont2 = dna.size()-cont-1;
                //Guardar las letras de la diagonal inversa superior en un string
                diagonalInvSup += dna.get(f).toUpperCase().charAt(cont2-f);
            }

            String diagonalInvInf = "";
            cont2 = cont;
            for (int f = dna.size()-1; f >= cont; f--) {
                //Guardar las letras de la diagonal inversa inferior en un string
                diagonalInvInf += dna.get(f).toUpperCase().charAt(cont2);
                cont2 += 1;
            }

            //Aumentar la cant de secuencias si hay una secuencia de cuatro letras iguales
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

    //Verificar si el srtring contiene alguna de las cuatro secuencias
    public static int cantidadSecuencias(String cadena){
        if (cadena.contains("AAAA") || cadena.contains("CCCC") || cadena.contains("GGGG") || cadena.contains("TTTT")){
            //Si contiene alguna de estas secuencias, devolver 1
            return 1;
        }
        //Si no contiene alguna de estas secuencias, devolver 0
        return 0;
    }
}