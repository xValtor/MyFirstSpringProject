package com.example.labs.services;

import com.example.labs.models.Product;
import com.example.labs.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> sortProducts(String sort) {
        if ("asc".equals(sort)) {
            return productRepository.findAllByOrderByPriceAsc();
        } else if ("desc".equals(sort)) {
            return productRepository.findAllByOrderByPriceDesc();
        }
        return productRepository.findAll();
    }
    public void update(Long id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

public List<Product> getProductsByFurnitureTypes(List<String> furnitureTypes, List<Product> products) {
    return products.stream()
            .filter(product -> furnitureTypes.contains(product.getFurnitureType().getName()))  // предполагаем, что у товара есть объект FurnitureType
            .collect(Collectors.toList());
}

    // Метод для получения продуктов по брендам
    public List<Product> getProductsByBrands(List<String> brands, List<Product> products) {
        return products.stream()
                .filter(product -> brands.contains(product.getBrand().getName()))  // предполагаем, что у товара есть объект Brand
                .collect(Collectors.toList());
    }

    // Метод для получения продуктов по странам
    public List<Product> getProductsByCountries(List<String> countries, List<Product> products) {
        return products.stream()
                .filter(product -> countries.contains(product.getCountry().getName()))  // предполагаем, что у товара есть объект Country
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByPriceRange(List<String> priceRange, List<Product> products) {
        // Пример фильтрации по диапазону цен
        return products.stream()
                .filter(product -> priceRange.contains(getPriceRange(product.getPrice())))  // предполагаем, что у товара есть цена
                .collect(Collectors.toList());
    }

    // Метод для получения продуктов по цене от и до
    public List<Product> getProductsByPriceFromTo(int priceFrom, int priceTo, List<Product> products) {
        return products.stream()
                .filter(product -> product.getPrice() >= priceFrom && product.getPrice() <= priceTo)
                .collect(Collectors.toList());
    }

    // Метод для получения ценового диапазона
    private String getPriceRange(double price) {
        if (price < 1000) {
            return "Менее 1000";
        } else if (price >= 1001 && price <= 2000) {
            return "1001-2000";
        } else if (price >= 2001 && price <= 5000) {
            return "2001-5000";
        } else if (price >= 5001 && price <= 10000) {
            return "5001-10000";
        } else if (price >= 10001 && price <= 20000) {
            return "10001-20000";
        } else if (price >= 20001 && price <= 50000) {
            return "20001-50000";
        } else {
            return "50001+";
        }
    }



}
