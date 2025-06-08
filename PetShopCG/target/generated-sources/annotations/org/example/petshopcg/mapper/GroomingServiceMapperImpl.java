package org.example.petshopcg.mapper;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.example.petshopcg.dto.GroomingServiceDto;
import org.example.petshopcg.entity.GroomingService;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-07T22:34:07+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Microsoft)"
)
@Component
public class GroomingServiceMapperImpl implements GroomingServiceMapper {

    @Override
    public GroomingServiceDto toDto(GroomingService entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        Boolean available = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        available = entity.getAvailable();

        GroomingServiceDto groomingServiceDto = new GroomingServiceDto( id, name, description, price, available );

        return groomingServiceDto;
    }

    @Override
    public GroomingService toEntity(GroomingServiceDto dto) {
        if ( dto == null ) {
            return null;
        }

        GroomingService groomingService = new GroomingService();

        groomingService.setId( dto.getId() );
        groomingService.setName( dto.getName() );
        groomingService.setDescription( dto.getDescription() );
        groomingService.setPrice( dto.getPrice() );
        groomingService.setAvailable( dto.getAvailable() );

        return groomingService;
    }
}
