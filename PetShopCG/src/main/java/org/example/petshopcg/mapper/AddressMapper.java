package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.AddressDto;
import org.example.petshopcg.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toDto(Address address);

    Address toEntity(AddressDto addressDto);

    List<AddressDto> toDtoList(List<Address> addressList);
}
