package ru.ekaterinakonova.restaurantvoting.repository.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.repository.RestaurantRepository;

@Repository
public class MenuRepositoryImpl {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public Menu save(Menu menu, int restaurant_id) {
        if (!menu.isNew() && get(menu.getId(), restaurant_id) == null) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getOne(restaurant_id));
        return menuRepository.save(menu);
    }

    public Menu get(int id, int restaurant_id) {
        return menuRepository.findById(id).filter(menu -> menu.getRestaurant().getId() == restaurant_id).orElse(null);
    }

    public boolean delete(int id, int restaurant_id) {
        return menuRepository.delete(id, restaurant_id) != 0;
    }
}

