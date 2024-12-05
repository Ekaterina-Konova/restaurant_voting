package ru.ekaterinakonova.restaurantvoting;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.service.*;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml","classpath:spring/spring-app.xml"})
@Transactional
@ExtendWith(TimingExtension.class)
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
