package com.example.labs.services;

import com.example.labs.DTO.BrandDTO;
import com.example.labs.models.Brand;
import com.example.labs.repo.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDTO> brandDTOs = new ArrayList<>();

        for (Brand brand : brands) {
            // Преобразование сущности в DTO
            brandDTOs.add(new BrandDTO(
                    brand.getId(),
                    brand.getName(),
                    brand.getCountry() != null ? brand.getCountry().getName() : null
            ));
        }

        return brandDTOs;
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    public List<Brand> getBrandsByCountryId(Long countryId) {
        return brandRepository.findByCountryId(countryId);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}