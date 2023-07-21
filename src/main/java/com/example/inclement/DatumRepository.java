package com.example.inclement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Date;
import java.util.List;

public interface DatumRepository extends JpaRepositoryImplementation<Datum, Long> {
    List<Datum> findAllBySensor(Long sensor);

    List<Datum> findAllBySensorAndUnit(Long sensor, Type unit);

    List<Datum> findAllByDatetimeAfter(Date datetime);

    List<Datum> findAllByUnit(Type unit);

    List<Datum> findAll();

    @Query("SELECT d FROM Datum d WHERE d.sensor IN :sensors AND d.unit = :unit AND d.datetime > :after")
    List<Datum> search(List<Long> sensors, Date after, Type unit);
}