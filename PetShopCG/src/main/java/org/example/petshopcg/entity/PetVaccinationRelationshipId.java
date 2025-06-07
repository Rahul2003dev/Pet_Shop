package org.example.petshopcg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PetVaccinationRelationshipId implements Serializable {
    private static final long serialVersionUID = 2016779478369999078L;
    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @NotNull
    @Column(name = "vaccination_id", nullable = false)
    private Integer vaccinationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PetVaccinationRelationshipId entity = (PetVaccinationRelationshipId) o;
        return Objects.equals(this.petId, entity.petId) &&
                Objects.equals(this.vaccinationId, entity.vaccinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, vaccinationId);
    }

}