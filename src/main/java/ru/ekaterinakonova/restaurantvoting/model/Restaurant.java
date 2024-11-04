package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="restaurant",uniqueConstraints = {@UniqueConstraint(columnNames = "name",name="unique_restaurant")})
public class Restaurant extends AbstractNamedEntity{
    public Restaurant() {

    }
    public Restaurant(Integer id,String name) {
        super(id,name);
    }
    @Override
    public String toString() {
        return "Restaurant{" +
                ", id=" + id +
                "name='" + name + '\'' +
                '}';
}
}
