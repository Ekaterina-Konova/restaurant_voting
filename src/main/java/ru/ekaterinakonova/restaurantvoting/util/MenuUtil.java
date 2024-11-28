package ru.ekaterinakonova.restaurantvoting.util;

import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.to.MenuTo;

public class MenuUtil {

    private MenuUtil() {
    }

    public static Menu menuFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getDate());
    }
}
