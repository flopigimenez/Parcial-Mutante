package com.example.parcial_programacion.repositories;

import com.example.parcial_programacion.model.entities.Human;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class HumanRepositoryTest {
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

    @Autowired
    private HumanRepository humanRepository;

    @Test
    public void testExistsByStrDna() {
        Human human = new Human();
        human.setStrDna("TAAAA-TGTGA-TGATC-TGGCG-TGCCC");
        humanRepository.save(human);

        boolean exists = humanRepository.existsByStrDna(human.getStrDna());
        assertThat(exists).isTrue();

        boolean notExists = humanRepository.existsByStrDna("TGTGA-TCCCC-TGGTC-TCCCG-GGGGC");
        assertThat(notExists).isFalse();
    }

    @Test
    public void testCountByIsmutant(){
        Human human = new Human();
        human.setIsmutant(true);
        humanRepository.save(human);

        Human human2 = new Human();
        human2.setIsmutant(false);
        humanRepository.save(human2);

        Human human3 = new Human();
        human3.setIsmutant(true);
        humanRepository.save(human3);

        assertThat(humanRepository.countByIsmutant(true)).isEqualTo(2);
        assertThat(humanRepository.countByIsmutant(false)).isEqualTo(1);
    }
}
