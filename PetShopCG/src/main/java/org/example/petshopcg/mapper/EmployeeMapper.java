package org.example.petshopcg.mapper;

import org.example.petshopcg.dto.EmployeeDto;
import org.example.petshopcg.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee entity);
    Employee toEntity(EmployeeDto dto);
}
