package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.VaccinationDto;
import org.example.petshopcg.entity.Vaccination;
import org.mapstruct.Mapper;

// This interface uses MapStruct to automatically generate mapping code at build time
@Mapper(componentModel = "spring")
public interface VaccinationMapper {

    // Converts Vaccination entity to VaccinationDto
    VaccinationDto toDto(Vaccination entity);

    // Converts VaccinationDto to Vaccination entity
    Vaccination toEntity(VaccinationDto dto);
}
