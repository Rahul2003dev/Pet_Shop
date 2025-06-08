package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.PetCategoryDto;
import org.example.petshopcg.entity.PetCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PetCategoryMapper {

    PetCategoryMapper INSTANCE = Mappers.getMapper(PetCategoryMapper.class);

    PetCategoryDto toDto(PetCategory entity);

    PetCategory toEntity(PetCategoryDto dto);
}
