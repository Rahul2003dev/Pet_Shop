package org.example.petshopcg.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Pet;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Pet}
 */
@Value
public class PetDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String name;
    @Size(max = 255)
    String breed;
    Integer age;
    BigDecimal price;
    @Size(max = 255)
    String description;
    @Size(max = 255)
    String imageUrl;
}