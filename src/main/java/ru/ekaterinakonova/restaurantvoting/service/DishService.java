package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.Dish;
import ru.ekaterinakonova.restaurantvoting.repository.dish.DishRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFound;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepositoryImpl repository;

    @Autowired
    public DishService(DishRepositoryImpl repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, menuId);
    }

    public void update(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, menuId), dish.getId());
    }

    public void delete(int id, int menuId) {
        checkNotFoundWithId(repository.delete(id, menuId), id);
    }
    public Dish get(int id, int menuId) {
       return checkNotFoundWithId(repository.get(id, menuId), id);
    }
    public List<Dish> getAll() {
        return repository.getAll();
    }
    public List<Dish> findByMenu(int menuId) {
       List<Dish> dishList=repository.findByMenuId(menuId);
       checkNotFoundWithId(!dishList.isEmpty(), menuId);
       return dishList;
    }
    public List<Dish> findByDate(LocalDate date) {
       Assert.notNull(date, "date must not be null");
       List<Dish> dishList=repository.findByDate(date);
       checkNotFound(!dishList.isEmpty(),date.toString());
       return dishList;
    }
}
