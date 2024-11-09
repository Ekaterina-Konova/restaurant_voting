package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.Restaurant;
import ru.ekaterinakonova.restaurantvoting.repository.RestaurantRepository;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestauranrService {
    private final RestaurantRepository repository;

    @Autowired
    public RestauranrService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
