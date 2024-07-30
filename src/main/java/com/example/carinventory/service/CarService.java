package com.example.carinventory.service;

import com.example.carinventory.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> searchCars(CarDto criteria);

    CarDto createCar(CarDto carDTO);
}
