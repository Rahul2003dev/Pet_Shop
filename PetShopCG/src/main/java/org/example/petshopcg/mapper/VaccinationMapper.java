package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.VaccinationDto;
import org.example.petshopcg.entity.Vaccination;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {
    VaccinationDto toDto(Vaccination entity);
    Vaccination toEntity(VaccinationDto dto);
}

