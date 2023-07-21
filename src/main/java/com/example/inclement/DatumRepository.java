package com.example.inclement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DatumRepository extends JpaRepositoryImplementation<Datum, Long> {
    List<Datum> findAllBySensor(Long sensor);

    List<Datum> findAllByDatetimeAfter(Date datetime);

    List<Datum> findAllByUnit(Type unit);

    @Query("SELECT d FROM Datum d WHERE d.sensor IN :sensors AND d.unit = :unit AND d.datetime BETWEEN NOW() AND :after")
    List<Datum> search(@Param("sensors") List<Long> sensors, Date after, Type unit);
}