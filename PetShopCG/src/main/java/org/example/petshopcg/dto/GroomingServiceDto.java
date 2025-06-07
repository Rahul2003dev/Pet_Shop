package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.GroomingService;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link GroomingService}
 */
@Value
public class GroomingServiceDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String name;
    @Size(max = 255)
    String description;
    BigDecimal price;
    Boolean available;
}