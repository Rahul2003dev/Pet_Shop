package org.example.petshopcg.dto;

public class PetCountByCategoryDto {
    private String categoryName;
    private long totalPets;

    public PetCountByCategoryDto(String categoryName, long totalPets) {
        this.categoryName = categoryName;
        this.totalPets = totalPets;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getTotalPets() {
        return totalPets;
    }
}
