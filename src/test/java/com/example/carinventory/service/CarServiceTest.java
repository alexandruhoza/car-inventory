package com.example.carinventory.service;

import com.example.carinventory.dto.CarDto;
import com.example.carinventory.model.Car;
import com.example.carinventory.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testFindCarsByCriteria_LengthOnly() {
        List<Car> expectedCars = Arrays.asList(
                new Car(1L, 4.5, 1500.0, 180.0, "Red"),
                new Car(2L, 4.5, 1500.0, 180.0, "Green")
        );

        when(carRepository.findByCriteria(eq(4.5), isNull(), isNull(), isNull()))
                .thenReturn(expectedCars);

        List<CarDto> cars = carService.searchCars(new CarDto(null, 4.5, null, null, null));
        assertEquals(2, cars.size());
        assertEquals(expectedCars.stream().map(el -> carService.convertToDto(el)).collect(Collectors.toList()), cars);
    }


    @Test
    void testFindCarsByCriteria_LengthAndColor() {
        List<Car> expectedCars = Arrays.asList(
                new Car(1L, 4.5, 1500.0, 180.0, "Red")
        );

        when(carRepository.findByCriteria(eq(4.5), isNull(), isNull(), eq("Red"))).thenReturn(expectedCars);

        List<CarDto> cars = carService.searchCars(new CarDto(null, 4.5, null, null, "Red"));
        assertEquals(1, cars.size());
        assertEquals(expectedCars.stream().map(el -> carService.convertToDto(el)).collect(Collectors.toList()), cars);
    }

    @Test
    void testFindCarsByCriteria_AllFields() {
        List<Car> expectedCars = Arrays.asList(
                new Car(5L, 4.5, 1500.0, 180.0, "Green")
        );

        when(carRepository.findByCriteria(eq(4.5), eq(1500.0), eq(180.0), eq("Green"))).thenReturn(expectedCars);

        List<CarDto> cars = carService.searchCars(new CarDto(null, 4.5, 1500.0, 180.0, "Green"));
        assertEquals(1, cars.size());
        assertEquals(expectedCars.stream().map(el -> carService.convertToDto(el)).collect(Collectors.toList()), cars);
    }

    @Test
    void testFindCarsByCriteria_NoMatch() {
        when(carRepository.findByCriteria(anyDouble(), anyDouble(), anyDouble(), anyString())).thenReturn(Arrays.asList());

        List<CarDto> cars = carService.searchCars(new CarDto(null, 4.5, 1500.0, 200.0, "Yellow"));
        assertEquals(0, cars.size());
    }

    @Test
    void testFindCarsByCriteria_NullParameters() {
        List<Car> expectedCars = Arrays.asList(
                new Car(1L, 4.5, 1500.0, 180.0, "Red"),
                new Car(2L, 4.0, 1400.0, 200.0, "Blue"),
                new Car(3L, 4.5, 1500.0, 180.0, "Green")
        );

        when(carRepository.findByCriteria(isNull(), isNull(), isNull(), isNull())).thenReturn(expectedCars);

        List<CarDto> cars = carService.searchCars(new CarDto(null, null, null, null, null));
        assertEquals(3, cars.size());
        assertEquals(expectedCars.stream().map(el -> carService.convertToDto(el)).collect(Collectors.toList()), cars);
    }
}
