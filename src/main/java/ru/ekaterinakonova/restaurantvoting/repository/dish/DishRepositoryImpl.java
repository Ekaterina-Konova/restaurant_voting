package ru.ekaterinakonova.restaurantvoting.repository.dish;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Dish;
import ru.ekaterinakonova.restaurantvoting.repository.menu.MenuRepository;

@Repository
public class DishRepositoryImpl {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.getId(), menuId) == null) {
            return null;
        }
        dish.setMenu(menuRepository.getOne(menuId));
        return dishRepository.save(dish);
    }

    public Dish get(int id, int menuId) {
        return dishRepository.findById(id).filter(dish -> dish.getMenu().getId() == menuId).orElse(null);
    }

    public boolean delete(int id, int menuId) {
        return dishRepository.delete(id, menuId) != 0;
    }
}
