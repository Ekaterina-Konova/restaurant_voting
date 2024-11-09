package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.repository.menu.MenuRepositoryImpl;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {
    private final MenuRepositoryImpl reepository;

    @Autowired
    public MenuService(MenuRepositoryImpl reepository) {
        this.reepository = reepository;
    }

    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return reepository.save(menu, restaurantId);
    }

    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(reepository.save(menu, restaurantId), menu.getId());
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(reepository.delete(id, restaurantId), id);
    }
}
