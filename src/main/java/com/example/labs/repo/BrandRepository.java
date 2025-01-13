package com.example.labs.repo;

import com.example.labs.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    // Найти бренд по имени
    Brand findByName(String name);
    // Найти все бренды по стране
    List<Brand> findByCountryId(Long countryId);
}
