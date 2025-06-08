package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.PetCategory;

import java.io.Serializable;

/**
 * DTO for {@link PetCategory}
 */
@Value
public class PetCategoryDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String name;
}