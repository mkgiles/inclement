package com.example.inclement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class WeatherController {
    private final DatumRepository datumRepository;

    WeatherController(DatumRepository datumRepository){
        this.datumRepository = datumRepository;
    }

    @GetMapping("/")
    List<Datum> all(){
        return this.datumRepository.findAll();
    }

    @GetMapping("/search")
    List<Datum> search(List<Long> sensors, Date after, Type type){
        return this.datumRepository.search(sensors, after, type);
    }

    @GetMapping("/sensor/{id}")
    List<Datum> sensor(@PathVariable Long id){
        return this.datumRepository.findAllBySensor(id);
    }
}
