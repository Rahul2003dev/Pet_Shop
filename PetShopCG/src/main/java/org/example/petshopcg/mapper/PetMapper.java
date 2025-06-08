package org.example.petshopcg.mapper;


import org.example.petshopcg.dto.PetDto;
import org.example.petshopcg.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Use "spring" to let Spring inject it as a bean
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    PetDto toDto(Pet pet);

    Pet toEntity(PetDto petDto);
}

