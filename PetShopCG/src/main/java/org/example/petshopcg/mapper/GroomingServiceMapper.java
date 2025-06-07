package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.GroomingServiceDto;
import org.example.petshopcg.entity.GroomingService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroomingServiceMapper {
    GroomingServiceDto toDto(GroomingService entity);
    GroomingService toEntity(GroomingServiceDto dto);
}
