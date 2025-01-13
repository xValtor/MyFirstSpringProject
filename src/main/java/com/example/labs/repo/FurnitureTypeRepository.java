package com.example.labs.repo;

import com.example.labs.models.FurnitureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnitureTypeRepository extends JpaRepository<FurnitureType, Long> {
    // Найти тип мебели по имени
    FurnitureType findByName(String name);
}