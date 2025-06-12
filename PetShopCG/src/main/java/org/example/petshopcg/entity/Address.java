package org.example.petshopcg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "street")
    private String street;

    @Size(max = 255)
    @Column(name = "city")
    private String city;

    @Size(max = 255)
    @Column(name = "state")
    private String state;

    @Size(max = 255)
    @Column(name = "zip_code")
    private String zipCode;

}