package ru.ekaterinakonova.restaurantvoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ekaterinakonova.restaurantvoting.service.UserService;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml"})
public class AbstractTest {
    @Autowired
    protected UserService userService;
}
