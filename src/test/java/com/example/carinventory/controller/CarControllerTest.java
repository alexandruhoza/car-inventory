package com.example.carinventory.controller;

import com.example.carinventory.dto.CarDto;
import com.example.carinventory.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testShowCarList() throws Exception {
        CarDto searchDto = new CarDto();
        searchDto.setColor("Red");
        searchDto.setLength(4.5);

        CarDto car1 = new CarDto();
        car1.setId(1L);
        car1.setColor("Red");
        car1.setLength(4.5);
        car1.setWeight(1500.0);
        car1.setVelocity(200.0);

        CarDto car2 = new CarDto();
        car2.setId(2L);
        car2.setColor("Red");
        car2.setLength(4.5);
        car2.setWeight(1400.0);
        car2.setVelocity(180.0);

        List<CarDto> cars = Arrays.asList(car1, car2);
        when(carService.searchCars(searchDto)).thenReturn(cars);

        mockMvc.perform(get("/cars/search")
                        .param("color", "Red")
                        .param("length", "4.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("car-list"))
                .andExpect(model().attribute("cars", hasSize(2)))
                .andExpect(model().attribute("cars", cars))
                .andExpect(model().attribute("carSearchDTO", searchDto));
    }

    @Test
    public void testDownloadCars() throws Exception {

        CarDto car1 = new CarDto();
        car1.setId(1L);
        car1.setColor("Red");
        car1.setLength(4.5);
        car1.setWeight(1500.0);
        car1.setVelocity(200.0);

        CarDto car2 = new CarDto();
        car2.setId(2L);
        car2.setColor("Red");
        car2.setLength(4.5);
        car2.setWeight(1400.0);
        car2.setVelocity(180.0);

        List<CarDto> cars = Arrays.asList(car1, car2);

        when(carService.searchCars(new CarDto(null, 4.5, null, null, "Red"))).thenReturn(cars);

        String xmlContent = """
                <?xml version="1.0" encoding="UTF-8"?>
                <cars>
                  <car>
                    <id>1</id>
                    <color>Red</color>
                    <length>4.5</length>
                    <weight>1500.0</weight>
                    <velocity>200.0</velocity>
                  </car>
                  <car>
                    <id>2</id>
                    <color>Red</color>
                    <length>4.5</length>
                    <weight>1400.0</weight>
                    <velocity>180.0</velocity>
                  </car>
                </cars>""";

        mockMvc.perform(post("/cars/download")
                        .param("color", "Red")
                        .param("length", "4.5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().xml(xmlContent))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml"));
    }

    @Test
    public void testCreateCar() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setColor("Red");
        carDto.setLength(4.5);
        carDto.setWeight(1500.0);
        carDto.setVelocity(200.0);

        mockMvc.perform(post("/cars/create")
                        .param("color", "Red")
                        .param("length", "4.5")
                        .param("weight", "1500")
                        .param("velocity", "200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("search"));
        verify(carService).createCar(carDto);
    }
}

