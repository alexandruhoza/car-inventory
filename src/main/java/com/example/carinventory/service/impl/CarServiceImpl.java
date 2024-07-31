package com.example.carinventory.service.impl;

import com.example.carinventory.dto.CarDto;
import com.example.carinventory.model.Car;
import com.example.carinventory.repository.CarRepository;
import com.example.carinventory.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<CarDto> searchCars(CarDto criteria) {
        log.info("search cars by criteria: {}", criteria);
        List<Car> cars = carRepository.findByCriteria(
                criteria.getLength(), criteria.getWeight(), criteria.getVelocity(), StringUtils.trimToNull(criteria.getColor()));
        return cars.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CarDto convertToDto(Car car) {
        return new CarDto(car.getId(), car.getLength(), car.getWeight(), car.getVelocity(), car.getColor());
    }

    @Override
    public CarDto createCar(CarDto carDTO) {
        log.info("create car: {}", carDTO);
        Car car = convertToEntity(carDTO);
        Car savedCar = carRepository.save(car);
        return convertToDto(savedCar);
    }

    private Car convertToEntity(CarDto carDto) {
        return new Car(carDto.getId(), carDto.getLength(), carDto.getWeight(), carDto.getVelocity(), carDto.getColor());
    }
}

