package com.example.inclement;

import jakarta.persistence.*;

import java.sql.Date;

enum Type {
    TEMPERATURE, HUMIDITY
}

@Entity
@Table(name = "datum")
public class Datum {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private Double measurement;
    private Date datetime;
    private Long sensor;
    @Enumerated(EnumType.STRING)
    private Type unit;

    protected Datum(){}

    public Datum(Long sensor, Type unit, Date datetime, Double measurement){
        this.sensor = sensor;
        this.unit = unit;
        this.datetime = datetime;
        this.measurement = measurement;
    }

    public Long getId() {
        return id;
    }

    public Double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Double measurement) {
        this.measurement = measurement;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Long getSensor() {
        return sensor;
    }

    public void setSensor(Long sensor) {
        this.sensor = sensor;
    }

    public Type getUnit() {
        return unit;
    }

    public void setUnit(Type unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return datetime + " " + unit.name() + "@" + id + ": " + measurement;
    }
}