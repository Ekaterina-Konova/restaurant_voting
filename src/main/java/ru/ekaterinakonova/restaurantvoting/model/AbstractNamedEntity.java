package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class AbstractNamedEntity extends AbstractBaseEntity{
    @NotBlank
    @Size(max = 128)
    @Column(name = "firstName", nullable = false)
    protected String firstName;

    @NotBlank
    @Size(max = 128)
    @Column(name = "lastName", nullable = false)
    protected String lastName;

    public AbstractNamedEntity() {

    }

    public AbstractNamedEntity(Integer id, String firstName, String lastName) {
        super(id);
    }

    @Override
    public String toString() {
        return super.toString() + '(' + firstName + " " + lastName + ')';
    }
}
