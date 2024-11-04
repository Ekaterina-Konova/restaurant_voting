package ru.ekaterinakonova.restaurantvoting.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"}, name = "unique_menu")})
public class Menu extends AbstractBaseEntity {
    @NotNull
    @Column(name = "menu_date", nullable = false)
    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name="restaurant_id",nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Menu(){
    }
    public Menu(Integer id, Restaurant restaurant, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
    }
    @Override
    public String toString() {
        return "Menu (" +
                "id=" + getId() +
                ", date=" + date +
                ')';
    }
}
