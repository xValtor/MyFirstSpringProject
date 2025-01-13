package com.example.labs.repo;


import com.example.labs.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();



    List<Product> findByFurnitureTypeNameIn(List<String> furnitureTypeNames);
    List<Product> findByBrandNameIn(List<String> brandNames);
    List<Product> findByCountryNameIn(List<String> countryNames);
    // Найти все продукты в пределах определенного ценового диапазона
    List<Product> findByPriceBetween(int minPrice, int maxPrice);
    // Найти все продукты по типу мебели
    List<Product> findByFurnitureTypeId(Long typeId);
    List<Product> findByBrandId(Long brandId);
}

