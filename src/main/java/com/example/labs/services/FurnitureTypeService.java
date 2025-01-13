package com.example.labs.services;

import com.example.labs.models.FurnitureType;
import com.example.labs.repo.FurnitureTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FurnitureTypeService {
    private final FurnitureTypeRepository furnitureTypeRepository;

    public FurnitureTypeService(FurnitureTypeRepository furnitureTypeRepository) {
        this.furnitureTypeRepository = furnitureTypeRepository;
    }

    public List<FurnitureType> getAllFurnitureTypes() {
        return furnitureTypeRepository.findAll();
    }

    public Optional<FurnitureType> getFurnitureTypeById(Long id) {
        return furnitureTypeRepository.findById(id);
    }

    public FurnitureType getFurnitureTypeByName(String name) {
        return furnitureTypeRepository.findByName(name);
    }

    public FurnitureType saveFurnitureType(FurnitureType furnitureType) {
        return furnitureTypeRepository.save(furnitureType);
    }

    public void deleteFurnitureType(Long id) {
        furnitureTypeRepository.deleteById(id);
    }
}
