package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Vaccination;

import java.io.Serializable;
import java.math.BigDecimal;

@Value // Lombok annotation to make the class immutable and generate required methods
public class VaccinationDto implements Serializable {

    Integer id; // Unique identifier for the vaccination

    @Size(max = 255) // Restricts the length of name to 255 characters
    String name; // Name of the vaccination

    @Size(max = 255) // Restricts the length of description to 255 characters
    String description; // Description or details about the vaccination

    BigDecimal price; // Cost of the vaccination

    Boolean available; // Availability status of the vaccination (true = available, false = not available)
}
