package com.example.carinventory.repository;

import com.example.carinventory.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE " +
            "(:length IS NULL OR c.length = :length) AND " +
            "(:weight IS NULL OR c.weight = :weight) AND " +
            "(:velocity IS NULL OR c.velocity = :velocity) AND " +
            "(:color IS NULL OR c.color = :color)")
    List<Car> findByCriteria(
            @Param("length") Double length,
            @Param("weight") Double weight,
            @Param("velocity") Double velocity,
            @Param("color") String color
    );
}
