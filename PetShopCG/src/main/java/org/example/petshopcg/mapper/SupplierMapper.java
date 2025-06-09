package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.SupplierDto;
import org.example.petshopcg.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierDto toDto(Supplier supplier) {
        if (supplier == null) return null;

        String city = null;
        String state = null;

        if (supplier.getAddress() != null) {
            city = supplier.getAddress().getCity();
            state = supplier.getAddress().getState();
        }

        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getPhoneNumber(),
                supplier.getEmail(),
                city,
                state
        );
    }

    public Supplier toEntity(SupplierDto dto) {
        if (dto == null) return null;

        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setEmail(dto.getEmail());

        // Skipping address setting — use a service/repository if address needs to be resolved by city/state
        return supplier;
    }
}
