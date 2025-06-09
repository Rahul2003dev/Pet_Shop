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
public class EmployeePetRelationshipId implements Serializable {
    private static final long serialVersionUID = 4578409373997278128L;
    @NotNull
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeePetRelationshipId entity = (EmployeePetRelationshipId) o;
        return Objects.equals(this.petId, entity.petId) &&
                Objects.equals(this.employeeId, entity.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, employeeId);
    }

}