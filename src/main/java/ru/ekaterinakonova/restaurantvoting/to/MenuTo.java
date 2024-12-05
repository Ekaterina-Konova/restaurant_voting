package ru.ekaterinakonova.restaurantvoting.to;

import lombok.Getter;
import lombok.Setter;
import ru.ekaterinakonova.restaurantvoting.model.Dish;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MenuTo extends BaseTo {
    @NotNull
    private LocalDate date;

    @NotNull
    private Dish[] menu;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDate date, Dish... dishes) {
        super(id);
        this.date = date;
        this.menu = dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", date=" + date +
                ", menu=" + menu +
                '}';
    }
}
