package ru.ekaterinakonova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "menu_id"}, name = "unique_dish")})
public class Dish extends AbstractNamedEntity {
    @Column(name = "cost", nullable = false)
    @Range(min = 1)
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

    public Dish(Dish d) {
        this(d.getId(), d.getDescription(), d.getCost());
    }

    public Dish(Integer id, String description, int cost) {
        super(id, description);
        this.cost = cost;
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
