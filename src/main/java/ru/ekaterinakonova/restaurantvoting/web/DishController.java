package ru.ekaterinakonova.restaurantvoting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ekaterinakonova.restaurantvoting.model.Dish;
import ru.ekaterinakonova.restaurantvoting.service.DishService;
import ru.ekaterinakonova.restaurantvoting.to.DishTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URL = "/rest/dishes";

    @Autowired
    private DishService service;

    @GetMapping
    public List<Dish> getAll() {
        log.info("Get all dishes");
        return service.getAll();
    }

    @GetMapping("/{menuId}/{id}")
    public Dish get(@PathVariable int id, @PathVariable int menuId) {
        log.info("Get dish with id {} for menuId={}", id, menuId);
        return service.get(id, menuId);
    }

    @PostMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated @RequestBody DishTo dishTo, @PathVariable int menuId) {
        log.info("Create {} for menuId={}", dishTo, menuId);
        checkNew(dishTo);
        Dish created = service.create(new Dish(dishTo.getId(), dishTo.getDescription(), dishTo.getCost()), menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuId}/{id}")
                .buildAndExpand(menuId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("{menuId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int menuId) {
        log.info("Delete {} for menuId={}", id, menuId);
        service.delete(id, menuId);
    }

    @PutMapping(value = "/{menuId}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody DishTo dishTo, @PathVariable int id, @PathVariable int menuId) {
        log.info("Update {} with id{} for menuId={}", dishTo, id, menuId);
        assureIdConsistent(dishTo, id);
        service.update(new Dish(dishTo.getId(), dishTo.getDescription(), dishTo.getCost()), menuId);
    }

    @GetMapping("/by")
    public List<Dish> findByDate(@RequestParam LocalDate date) {
        log.info("Find by date {}", date);
        return service.findByDate(date);
    }

    @GetMapping("/{menuId}")
    public List<Dish> findByMenuId(@PathVariable int menuId) {
        log.info("Find by menuId={}", menuId);
        return service.findByMenu(menuId);
    }
}
