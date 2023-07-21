package com.example.inclement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
 class WeatherControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private DatumRepository datumRepository;

    private List<Datum> data;
    @BeforeEach
    void setUp() throws  Exception {
        data = new ArrayList<>();
        data.add(new Datum(1L,Type.TEMPERATURE,new Date(),23d));
        data.add(new Datum(2L, Type.HUMIDITY,new Date(), 85.1));
        Mockito.when(datumRepository.findAll()).thenReturn(data);
        Mockito.when(datumRepository.search(Mockito.anyList(),Mockito.any(Date.class),Mockito.any(Type.class))).thenReturn(Collections.singletonList(data.get(0)));
        Mockito.when(datumRepository.findAllBySensor(1L)).thenReturn(Collections.singletonList(data.get(0)));
    }

    @Test
    void testAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSearch() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/search")
                        .param("sensors", "1,2")
                        .param("type", "TEMPERATURE")
                        .param("after", "2023-07-20")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    void testBySensor() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .get("/sensor/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
