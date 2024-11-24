package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.repository.menu.MenuRepositoryImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFound;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {
    private final MenuRepositoryImpl repository;

    @Autowired
    public MenuService(MenuRepositoryImpl repository) {
        this.repository = repository;
    }

    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu, restaurantId);
    }

    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(repository.save(menu, restaurantId), menu.getId());
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }
    public Menu get(int id, int restaurant_id) {
       return checkNotFoundWithId(repository.get(id, restaurant_id), id);
    }
    public List<Menu> getAll(int restaurant_id) {
        return repository.getAll();
    }
    public List<Menu> findByRestaurantId(int restaurant_id) {
        List<Menu> menuList = repository.findByRestaurant(restaurant_id);
        checkNotFoundWithId(!menuList.isEmpty(), restaurant_id);
        return menuList;
    }
    public List<Menu> findByDate(LocalDate date) {
       Assert.notNull(date, "date must not be null");
        List<Menu> menuList = repository.findByDate(date);
        checkNotFound(!menuList.isEmpty(),date.toString());
        return menuList;
    }
}
