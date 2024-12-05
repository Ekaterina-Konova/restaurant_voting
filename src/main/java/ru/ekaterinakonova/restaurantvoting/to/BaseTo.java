package ru.ekaterinakonova.restaurantvoting.to;

import lombok.Getter;
import lombok.Setter;
import ru.ekaterinakonova.restaurantvoting.HasId;

@Getter
@Setter
public class BaseTo implements HasId {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }
}
