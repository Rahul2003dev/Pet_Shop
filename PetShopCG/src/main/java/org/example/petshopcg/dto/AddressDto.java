package org.example.petshopcg.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.example.petshopcg.entity.Address;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String street;
    @Size(max = 255)
    String city;
    @Size(max = 255)
    String state;
    @Size(max = 255)
    String zipCode;
}