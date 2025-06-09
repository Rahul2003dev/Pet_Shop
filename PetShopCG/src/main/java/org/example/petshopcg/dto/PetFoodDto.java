package org.example.petshopcg.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data; // ✅ Use this instead of @Value
import org.example.petshopcg.entity.PetFood;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link PetFood}
 */
@Data // ✅ Generates getters, setters, equals, hashCode, toString
public class PetFoodDto implements Serializable {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String brand;

    @Size(max = 255)
    private String type;

    private Integer quantity;

    private BigDecimal price;

    // ✅ Default constructor (Jackson needs this)
    public PetFoodDto() {
    }

    // Optional: all-args constructor (useful for manual creation)
    public PetFoodDto(Integer id, String name, String brand, String type, Integer quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }
}
