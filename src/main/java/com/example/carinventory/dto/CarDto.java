package com.example.carinventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private Double length;
    private Double weight;
    private Double velocity;
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return Objects.equals(id, carDto.id) && Objects.equals(length, carDto.length) && Objects.equals(weight, carDto.weight) && Objects.equals(velocity, carDto.velocity) && Objects.equals(color, carDto.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, weight, velocity, color);
    }
}
