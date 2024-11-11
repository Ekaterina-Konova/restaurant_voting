package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "restaurant_id"}, name = "unidue_dish")})
public class Dish extends AbstractNamedEntity {
    @Column(name = "cost", nullable = false)
    @Range(min = 0)
    private BigDecimal cost;

    @Column(name = "description", nullable = false)
    @Size(min = 2, max = 250)
    @NotEmpty
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "menu_date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate menuDate;

    public Dish(String description, BigDecimal cost, Restaurant restaurant, LocalDate menuDate) {
        this(null, description, cost, restaurant, menuDate);
    }

    public Dish(Integer id, String description, BigDecimal cost, Restaurant restaurant, LocalDate menuDate) {
        super(id);
        this.description = description;
        this.cost = cost;
        this.restaurant = restaurant;
        this.menuDate = menuDate;
    }

    public Dish(Integer id, String description, BigDecimal cost, LocalDate menuDate) {
        super(id);
        this.description = description;
        this.cost = cost;
        this.menuDate = menuDate;
    }

    public Dish() {
    }

    public String toString() {
        return "Dish [" +
                "id=" + id +
                "description=" + description +
                "cost=" + cost +
                "menuDate=" + menuDate +
                "]";
    }

}
