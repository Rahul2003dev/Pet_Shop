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
public class PetGroomingRelationshipId implements Serializable {
    private static final long serialVersionUID = -4564451525343777230L;
    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PetGroomingRelationshipId entity = (PetGroomingRelationshipId) o;
        return Objects.equals(this.petId, entity.petId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, serviceId);
    }

}