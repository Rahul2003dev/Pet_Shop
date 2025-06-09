package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.petshopcg.entity.Supplier;

import java.io.Serializable;

/**
 * DTO for {@link Supplier}
 */
@Value
public class SupplierDto implements Serializable {
    Integer id;

    @Size(max = 255)
    String name;

    @Size(max = 255)
    String contactPerson;

    @Size(max = 255)
    String phoneNumber;

    @Size(max = 255)
    String email;

    @Size(max = 255)
    String city;   // new field

    @Size(max = 255)
    String state;  // new field
}
