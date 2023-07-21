package com.example.inclement;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DatumRepositoryTests {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private DatumRepository datumRepository;

    @Test
    void injectedComponentsNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(datumRepository).isNotNull();
    }

    @Test
    void insertTest(){
        Datum datum = new Datum(1L,Type.TEMPERATURE,new Date(),23d);
        datumRepository.save(datum);
        assertThat(datumRepository.getReferenceById(datum.getId())).isEqualTo(datum);
    }

    @Test
    void findTest(){
        List<Datum> data = datumRepository.findAll();
        assertThat(data).isEmpty();
        datumRepository.save(new Datum(1L, Type.TEMPERATURE, new Date(), 23d));
        data = datumRepository.findAll();
        assertThat(data).hasAtLeastOneElementOfType(Datum.class);
        data = datumRepository.findAllBySensor(2L);
        assertThat(data).isEmpty();
    }

    @Test
    void searchTest(){
        LocalDate ld = LocalDate.now();
        Date yesterday = Date.from(ld.minusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        List<Datum> data = datumRepository.search(Arrays.asList(1L,2L),yesterday,Type.TEMPERATURE);
        assertThat(data).isEmpty();
        datumRepository.save(new Datum(1L, Type.TEMPERATURE, new Date(), 23d));
        datumRepository.save(new Datum(2L, Type.TEMPERATURE, new Date(), 28d));
        datumRepository.save(new Datum(1L, Type.HUMIDITY, new Date(), 85d));
        datumRepository.flush();
        data = datumRepository.search(Arrays.asList(1L,2L),yesterday,Type.TEMPERATURE);
        assertThat(data).hasAtLeastOneElementOfType(Datum.class);
        assertThat(data).hasSize(2);
        data = datumRepository.search(Arrays.asList(1L,2L),yesterday,Type.HUMIDITY);
        assertThat(data).hasAtLeastOneElementOfType(Datum.class);
        assertThat(data).hasSize(1);
        data = datumRepository.search(Arrays.asList(3L,4L),yesterday,Type.HUMIDITY);
        assertThat(data).isEmpty();
    }

}
