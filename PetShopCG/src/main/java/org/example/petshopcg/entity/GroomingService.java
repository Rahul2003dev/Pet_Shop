package org.example.petshopcg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "grooming_services")
public class GroomingService {
    @Id
    @Column(name = "service_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 38, scale = 2)
    private BigDecimal price;

    @ColumnDefault("1")
    @Column(name = "available")
    private Boolean available;

}