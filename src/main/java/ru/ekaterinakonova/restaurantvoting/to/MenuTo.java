package ru.ekaterinakonova.restaurantvoting.to;

import lombok.Getter;
import ru.ekaterinakonova.restaurantvoting.model.AbstractBaseEntity;
import ru.ekaterinakonova.restaurantvoting.model.Dish;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class MenuTo extends AbstractBaseEntity {
    @NotNull
    private final LocalDate date;

    @NotNull
    private final List<Dish> menu;

    public MenuTo(Integer id, LocalDate date, Dish... dishes) {
        super(id);
        this.date = date;
        this.menu = List.of(dishes);
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
