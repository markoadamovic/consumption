package com.example.consumption.repository;

import com.example.consumption.model.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
}
