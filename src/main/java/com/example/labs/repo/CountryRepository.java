package com.example.labs.repo;

import com.example.labs.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    // Найти страну по имени
    Country findByName(String name);
}
