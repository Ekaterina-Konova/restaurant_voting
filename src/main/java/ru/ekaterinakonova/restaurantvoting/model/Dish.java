package ru.ekaterinakonova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

@Entity
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "menu_id"}, name = "unique_dish")})
public class Dish extends AbstractBaseEntity {
    @Column(name = "cost", nullable = false)
    @Range(min = 0)
    private int cost;

    @Column(name = "description", nullable = false)
    @Size(min = 2, max = 250)
    @NotEmpty
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private Menu menu;

    public Dish(Integer id, String description, Integer cost) {
        super(id);
        this.description=description;
        this.cost=cost;
    }

    public Dish(String description,Integer cost, Menu menu) {
        this(null, description, cost, menu);
    }

    public Dish(Integer id, String description, Integer cost, Menu menu) {
        super(id);
        this.description = description;
        this.cost = cost;
        this.menu = menu;
    }


    public Dish() {
    }

    public String toString() {
        return "Dish (" +
                "id=" + id +
                "description=" + description +
                "cost=" + cost +
                ")";
    }
}
