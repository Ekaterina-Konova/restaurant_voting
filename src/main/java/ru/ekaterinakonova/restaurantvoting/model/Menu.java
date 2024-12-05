package ru.ekaterinakonova.restaurantvoting.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"}, name = "unique_menu")})
public class Menu extends AbstractBaseEntity {
    @NotNull
    @Column(name = "menu_date", nullable = false)
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "menu",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("name ASC")
    private List<Dish> dishes= Collections.emptyList();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="restaurant_id",nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Menu(){
    }
    public Menu(Menu m){
        this(m.getId(),m.getDate());
    }
    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }
    public void setDishes(List<Dish> dishes) {
        this.dishes.addAll(dishes);
    }
    @Override
    public String toString() {
        return "Menu (" +
                "id=" + getId() +
                ", date=" + date +
                ')';
    }
}
