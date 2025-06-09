package org.example.petshopcg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pet_vaccination_relationship")
public class PetVaccinationRelationship {
    @EmbeddedId
    private PetVaccinationRelationshipId id;

    @MapsId("petId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @MapsId("vaccinationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vaccination_id", nullable = false)
    private Vaccination vaccination;

}