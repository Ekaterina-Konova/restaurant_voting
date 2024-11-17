package ru.ekaterinakonova.restaurantvoting.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class AbstractNamedEntity extends AbstractBaseEntity{
    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false)
    protected String name;

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

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
    public AbstractNamedEntity(Integer id,String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + firstName + " " + lastName + ')';
    }
}
