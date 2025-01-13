package com.example.labs.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "furniture_types")
public class FurnitureType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
