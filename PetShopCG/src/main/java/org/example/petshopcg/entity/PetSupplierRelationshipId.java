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
public class PetSupplierRelationshipId implements Serializable {
    private static final long serialVersionUID = -5234725261075404375L;
    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @NotNull
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PetSupplierRelationshipId entity = (PetSupplierRelationshipId) o;
        return Objects.equals(this.petId, entity.petId) &&
                Objects.equals(this.supplierId, entity.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, supplierId);
    }

}