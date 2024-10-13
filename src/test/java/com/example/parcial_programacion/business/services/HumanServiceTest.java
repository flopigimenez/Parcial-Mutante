package com.example.parcial_programacion.business.services;

import com.example.parcial_programacion.business.services.impl.HumanService;
import com.example.parcial_programacion.model.dtos.HumanDto;
import com.example.parcial_programacion.model.dtos.StatsDto;
import com.example.parcial_programacion.repositories.HumanRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//Test HumanService
@SpringBootTest
public class HumanServiceTest {
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
    private HumanRepository humanRepository;

    @Autowired
    private HumanService humanService;

    //Test para verificar que se lanza una excepción cuando el adn es nulo o vacío
    @Test
    public void testSave_shouldThrowException_null() {
        HumanDto humanDto = new HumanDto();
        humanDto.setDna(new ArrayList<>());

        assertThatThrownBy(() -> humanService.save(humanDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El ADN no puede ser nulo o vacío");
    }

    //Test para verificar que se lanza una excepción cuando el adn contiene letras no permitidas
    @Test
    public void testSave_shouldThrowException_letter() {
        List<String> dna = new ArrayList<>();
        dna.add("ACTGGT");
        dna.add("AFDVGG");
        dna.add("ACPAFT");
        dna.add("ACREET");
        dna.add("ACTSST");
        dna.add("AGKSGG");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);

        assertThatThrownBy(() -> humanService.save(humanDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Solo puede contener las letras A,T,C,G");
    }

    //Test para verificar que se lanza una excepción cuando el adn no tiene una dimensión es NxN
    @Test
    public void testSave_shouldThrowException_size() {
        List<String> dna = new ArrayList<>();
        dna.add("ACTG");
        dna.add("AGGC");
        dna.add("ACGA");
        dna.add("ACTC");
        dna.add("ACTT");
        dna.add("AGCT");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);

        assertThatThrownBy(() -> humanService.save(humanDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El adn debe tener una dimension de NxN");
    }

    //Test para verificar que se lanza una excepción cuando el adn ya existe
    @Test
    public void testSave_shouldThrowException_exist(){
        List<String> dna = new ArrayList<>();
        dna.add("ACTGGT");
        dna.add("AGGCGT");
        dna.add("ACGAAT");
        dna.add("ACTCTT");
        dna.add("ACTTTT");
        dna.add("AGCTGG");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);

        when(humanRepository.existsByStrDna(anyString())).thenReturn(true);

        assertThatThrownBy(() -> humanService.save(humanDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El ADN no puede ser existente");

    }

    //Test para verificar que el adn es mutante
    @Test
    public void testSave_ismutant(){
        List<String> dna = new ArrayList<>();
        dna.add("ACTCTT");
        dna.add("ACTTTT");
        dna.add("AGCTGG");
        dna.add("ACTGGT");
        dna.add("AGGCGT");
        dna.add("ACGAAT");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Test para verificar que el adn no es mutante
    @Test
    public void testSave_notmutant() {
        List<String> dna = new ArrayList<>();
        dna.add("ACTCTT");
        dna.add("CCTATT");
        dna.add("CGCTGG");
        dna.add("ACTGGT");
        dna.add("AGGCGT");
        dna.add("ACGAAT");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean notmutant = humanService.save(humanDto);
        assertThat(notmutant).isFalse();
    }

    //Tests enviados por el profe
    //Verificar mutante
    @Test
    public void testSave_ismutant_1(){
        List<String> dna = new ArrayList<>();
        dna.add("AAAA");
        dna.add("CCCC");
        dna.add("TCAG");
        dna.add("GGTC");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Verificar mutante
    @Test
    public void testSave_ismutant_2(){
        List<String> dna = new ArrayList<>();
        dna.add("TGAC");
        dna.add("AGCC");
        dna.add("TGAC");
        dna.add("GGTC");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Verificar mutante
    @Test
    public void testSave_ismutant_3(){
        List<String> dna = new ArrayList<>();
        dna.add("AAAA");
        dna.add("AAAA");
        dna.add("AAAA");
        dna.add("AAAA");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Verificar mutante
    @Test
    public void testSave_ismutant_4(){
        List<String> dna = new ArrayList<>();
        dna.add("TCGGGTGAT");
        dna.add("TGATCCTTT");
        dna.add("TACGAGTGA");
        dna.add("AAATGTACG");
        dna.add("ACGAGTGCT");
        dna.add("AGACACATG");
        dna.add("GAATTCCAA");
        dna.add("ACTACGACC");
        dna.add("TGAGTATCC");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Verificar mutante
    @Test
    public void testSave_ismutant_5(){
        List<String> dna = new ArrayList<>();
        dna.add("TTTTTTTTT");
        dna.add("TTTTTTTTT");
        dna.add("TTTTTTTTT");
        dna.add("TTTTTTTTT");
        dna.add("CCGACCAGT");
        dna.add("GGCACTCCA");
        dna.add("AGGACACTA");
        dna.add("CAAAGGCAT");
        dna.add("GCAGTCCCC");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean ismutant = humanService.save(humanDto);
        assertThat(ismutant).isTrue();
    }

    //Verificar no mutante
    @Test
    public void testSave_notmutant_1() {
        List<String> dna = new ArrayList<>();
        dna.add("TGAC");
        dna.add("ATCC");
        dna.add("TAAG");
        dna.add("GGTC");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean notmutant = humanService.save(humanDto);
        assertThat(notmutant).isFalse();
    }

    //Verificar no mutante
    @Test
    public void testSave_notmutant_2() {
        List<String> dna = new ArrayList<>();
        dna.add("AAAT");
        dna.add("AACC");
        dna.add("AAAC");
        dna.add("CGGG");

        HumanDto humanDto = new HumanDto();
        humanDto.setDna(dna);
        boolean notmutant = humanService.save(humanDto);
        assertThat(notmutant).isFalse();
    }

    //Test para verificar las estadísticas de mutantes y humanos
    @Test
    public void testStats(){
        Long countMutantDna = 5L;
        Long countHumanDna = 2L;
        double ratio = 2.5;

        when(humanRepository.countByIsmutant(true)).thenReturn(countMutantDna);
        when(humanRepository.countByIsmutant(false)).thenReturn(countHumanDna);

        StatsDto statsDto = StatsDto.builder()
                .countMutantDna(countMutantDna)
                .countHumanDna(countHumanDna)
                .ratio(ratio)
                .build();

        StatsDto stats = humanService.stats();
        assertThat(statsDto.getCountMutantDna()).isEqualTo(countMutantDna);
        assertThat(statsDto.getCountHumanDna()).isEqualTo(countHumanDna);
        assertThat(stats.getRatio()).isEqualTo(statsDto.getRatio());
    }
}