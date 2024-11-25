package ru.ekaterinakonova.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.ekaterinakonova.restaurantvoting.data.MenuTestData;
import ru.ekaterinakonova.restaurantvoting.model.Dish;
import ru.ekaterinakonova.restaurantvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ekaterinakonova.restaurantvoting.data.DishTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.*;


public class DishServiceTest extends AbstractServiceTest {
    @Autowired
    private DishService service;

    @Test
    void create() throws Exception {
        Dish newDish = new Dish(null, "New dish", 10000);
        Dish created = service.create(new Dish(newDish), MenuTestData.MENU_ID_1);
        newDish.setId(created.getId());
        assertMatch(created, newDish);
        assertMatch(service.getAll(), DISH_3, DISH_2, DISH_4, DISH_5, newDish, DISH_1, DISH_6);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () -> service.create(new Dish(null, "Steak", 1000), MenuTestData.MENU_ID_1));
    }

    @Test
    void delete() throws Exception {
        service.delete(DISH_ID_6, MENU_ID_4);
        assertMatch(service.getAll(), DISH_3, DISH_2, DISH_4, DISH_5, DISH_1);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_ID_6, MENU_ID_1));
    }

    @Test
    void get() throws Exception {
        Dish dish = service.get(DISH_ID_1, MENU_ID_1);
        assertMatch(dish, DISH_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID_1, MENU_ID_2));
    }

    @Test
    void findByDate() throws Exception {
        List<Dish> dishList = service.findByDate(LocalDate.of(2019, 6, 11));
        assertMatch(dishList, DISH_3, DISH_4, DISH_5, DISH_6);
    }

    @Test
    void findByDateNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.findByDate(LocalDate.of(3000, 6, 11)));
    }

    @Test
    void findByMenu() throws Exception {
        List<Dish> dishList = service.findByMenu(MENU_ID_3);
        assertMatch(dishList, DISH_3, DISH_4, DISH_5);
    }

    @Test
    void findByMenuNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.findByMenu(1));
    }

    @Test
    void update() throws Exception {
        Dish updated = new Dish(DISH_1);
        updated.setName("UpdatedName");
        updated.setCost(100);
        service.update(new Dish(updated), MENU_ID_1);
        assertMatch(service.get(DISH_ID_1, MENU_ID_1), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Dish> all = service.getAll();
        assertMatch(all, DISH_3, DISH_2, DISH_4, DISH_5, DISH_1, DISH_6);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "", 100), MENU_ID_1), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "name", -1), MENU_ID_1), ConstraintViolationException.class);
    }
}
