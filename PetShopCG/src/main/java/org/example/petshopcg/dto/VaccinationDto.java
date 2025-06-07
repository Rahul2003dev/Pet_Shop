package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Vaccination;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Vaccination}
 */
@Value
public class VaccinationDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String name;
    @Size(max = 255)
    String description;
    BigDecimal price;
    Boolean available;
}