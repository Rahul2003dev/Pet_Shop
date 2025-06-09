package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.PetFoodDto;
import org.example.petshopcg.entity.PetFood;
import org.springframework.stereotype.Component;

@Component
public class PetFoodMapper {

    // Convert Entity to DTO
    public PetFoodDto toDto(PetFood petFood) {
        if (petFood == null) return null;

        return new PetFoodDto(
                petFood.getId(),
                petFood.getName(),
                petFood.getBrand(),
                petFood.getType(),
                petFood.getQuantity(),
                petFood.getPrice()
        );
    }

    // Convert DTO to Entity
    public PetFood toEntity(PetFoodDto dto) {
        if (dto == null) return null;

        PetFood petFood = new PetFood();
        petFood.setId(dto.getId());
        petFood.setName(dto.getName());
        petFood.setBrand(dto.getBrand());
        petFood.setType(dto.getType());
        petFood.setQuantity(dto.getQuantity());
        petFood.setPrice(dto.getPrice());
        return petFood;
    }
}
