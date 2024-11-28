package ru.ekaterinakonova.restaurantvoting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.service.MenuService;
import ru.ekaterinakonova.restaurantvoting.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.MenuUtil.menuFromTo;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URL = "/rest/menu";

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> getAll() {
        log.info("getAll");
        return menuService.getAll();
    }

    @GetMapping("/{restaurant_id}/{id}")
    public Menu get(@PathVariable int id, @PathVariable int restaurant_id) {
        log.info("get {} for id={}", id, restaurant_id);
        return menuService.get(id, restaurant_id);
    }

    @PostMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurant_id) {
        log.info("create {} for id={}", menuTo, restaurant_id);
        checkNew(menuTo);
        Menu created = menuService.create(menuFromTo(menuTo), restaurant_id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(restaurant_id, created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("{restaurant_id}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurant_id) {
        log.info("delete {} for id={}", id, restaurant_id);
        menuService.delete(id, restaurant_id);
    }

    @PutMapping(value = "/{restaurant_id}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id, @PathVariable int restaurant_id) {
        log.info("update {} with id={} for id={}", menuTo, id, restaurant_id);
        assureIdConsistent(menuTo, id);
        menuService.update(menuFromTo(menuTo), restaurant_id);
    }

    @GetMapping("/byDate")
    public List<Menu> findByDate(@RequestParam LocalDate date) {
        log.info("findByDate {}", date);
        return menuService.findByDate(date);
    }

    @GetMapping("/byRestaurant")
    public List<Menu> findByRestaurant(@RequestParam int restaurant_id) {
        log.info("findByRestaurant {}", restaurant_id);
        return menuService.findByRestaurant(restaurant_id);
    }

    @GetMapping("byId")
    public Menu findById(@RequestParam int id) {
        log.info("findById {}", id);
        return menuService.findById(id);
    }
}
