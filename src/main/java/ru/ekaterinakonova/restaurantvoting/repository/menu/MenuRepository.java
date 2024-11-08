package ru.ekaterinakonova.restaurantvoting.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT m FROM Menu m WHERE m.date=:date")
    List<Menu> findByDate(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant=:restaurant")
    List<Menu> findByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Override
    @Transactional
    Menu save(Menu menu);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurant_id")
    int delete(@Param("id") int id, @Param("restaurant_id") int restaurant_id);
}
