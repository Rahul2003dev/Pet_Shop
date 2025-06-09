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
public class PetFoodRelationshipId implements Serializable {
    private static final long serialVersionUID = -6648679978129224240L;
    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @NotNull
    @Column(name = "food_id", nullable = false)
    private Integer foodId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PetFoodRelationshipId entity = (PetFoodRelationshipId) o;
        return Objects.equals(this.petId, entity.petId) &&
                Objects.equals(this.foodId, entity.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, foodId);
    }

}