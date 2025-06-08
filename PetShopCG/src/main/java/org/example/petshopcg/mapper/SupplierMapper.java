package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.SupplierDto;
import org.example.petshopcg.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierDto toDto(Supplier supplier) {
        if (supplier == null) return null;

        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getPhoneNumber(),
                supplier.getEmail()
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
        return supplier;
    }
}
