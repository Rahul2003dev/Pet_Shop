package org.example.petshopcg.dto;

import lombok.Value;

@Value
public class PetCountByBreedDto {
    String breed;
    Long count;
}
