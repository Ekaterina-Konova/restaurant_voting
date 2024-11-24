package ru.ekaterinakonova.restaurantvoting.repository.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MenuRepositoryImpl {
    private static final Sort SORT_DATE=new Sort(Sort.Direction.ASC, "date");
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
        return menuRepository.get(id, restaurant_id);
    }
    public List<Menu> findByDate(LocalDate date) {
        return menuRepository.findByDate(date);
    }
    public List<Menu> findByRestaurant(int restaurant_id) {
        return menuRepository.findByRestaurant(restaurant_id);
    }
    public List<Menu> getAll() {
        return menuRepository.findAll(SORT_DATE);
    }

    public boolean delete(int id, int restaurant_id) {
        return menuRepository.delete(id, restaurant_id) != 0;
    }
}

