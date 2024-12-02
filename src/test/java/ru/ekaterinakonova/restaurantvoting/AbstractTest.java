package ru.ekaterinakonova.restaurantvoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ekaterinakonova.restaurantvoting.service.*;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml"})
public class AbstractTest {
    @Autowired
    protected UserService userService;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected VoteService voteService;
}
