package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "unique_restaurant")})
public class Restaurant extends AbstractNamedEntity {
    public Restaurant() {
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName()) ;
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                ", id=" + id +
                "name='" + name + '\'' +
                '}';
    }
}
