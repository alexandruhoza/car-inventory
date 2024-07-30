package com.example.carinventory.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
public class CarList {
    private List<CarDto> cars;

    public CarList() {
    }

    public CarList(List<CarDto> cars) {
        this.cars = cars;
    }

    @XmlElement(name = "car")
    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
