package ru.ekaterinakonova.restaurantvoting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ekaterinakonova.restaurantvoting.model.Restaurant;
import ru.ekaterinakonova.restaurantvoting.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URI = "/rest/restaurants";
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("Get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("Get restaurant by id: {}", id);
        return restaurantService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("Create restaurant: {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URI + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant by id: {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody Restaurant restaurant) {
        log.info("Update {} by id: {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping("/by")
    public Restaurant findByName(@RequestParam String name) {
        log.info("Find restaurant by name: {}", name);
        return restaurantService.findByName(name);
    }
}
