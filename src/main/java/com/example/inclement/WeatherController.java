package com.example.inclement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class WeatherController {
    private final DatumRepository datumRepository;

    WeatherController(@Autowired DatumRepository datumRepository){
        this.datumRepository = datumRepository;
    }

    @GetMapping("/")
    List<Datum> all(){
        return this.datumRepository.findAll();
    }

    @GetMapping("/search")
    List<Datum> search(@RequestParam List<Long> sensors, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date after, @RequestParam Type type){
        return this.datumRepository.search(sensors, after, type);
    }

    @GetMapping("/sensor/{id}")
    List<Datum> sensor(@PathVariable Long id){
        return this.datumRepository.findAllBySensor(id);
    }

    @GetMapping("/type/{unit}")
    List<Datum> unit(@PathVariable String unit){
        return this.datumRepository.findAllByUnit(Type.valueOf(unit));
    }

    @GetMapping("/sensor/temp/{id}")
    List<Datum> tempSensor(@PathVariable Long id){
        return this.datumRepository.findAllBySensorAndUnit(id, Type.TEMPERATURE);
    }

    @GetMapping("/sensor/hum/{id}")
    List<Datum> humSensor(@PathVariable Long id){
        return this.datumRepository.findAllBySensorAndUnit(id, Type.HUMIDITY);
    }

    @GetMapping("/since/{after}")
    List<Datum> since(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date after){
        return this.datumRepository.findAllByDatetimeAfter(after);
    }

    @PostMapping("/upload")
    void addDatum(@RequestBody Datum body){
        this.datumRepository.saveAndFlush(body);
    }

    @PostMapping("/uploadBulk")
    void addData(@RequestBody List<Datum> body){
        this.datumRepository.saveAllAndFlush(body);
    }

    @GetMapping("/avg/search")
    Double searchAvg(@RequestParam List<Long> sensors, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date after, @RequestParam Type type){
        List<Datum> data = this.search(sensors, after, type);
        return data.stream().mapToDouble(Datum::getMeasurement).average().getAsDouble();
    }

    @GetMapping("/max/search")
    Double searchMax(@RequestParam List<Long> sensors, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date after, @RequestParam Type type){
        List<Datum> data = this.search(sensors,after,type);
        return data.stream().mapToDouble(Datum::getMeasurement).max().getAsDouble();
    }

    @GetMapping("/min/search")
    Double searchMin(@RequestParam List<Long> sensors, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date after, @RequestParam Type type){
        List<Datum> data = this.search(sensors,after,type);
        return data.stream().mapToDouble(Datum::getMeasurement).min().getAsDouble();
    }
}
