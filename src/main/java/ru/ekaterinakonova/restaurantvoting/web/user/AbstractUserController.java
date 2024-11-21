package ru.ekaterinakonova.restaurantvoting.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.service.UserService;

import java.util.List;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected UserService service;

    public List<User> getAll() {
        log.info("Get all users");
        return service.getAll();
    }

    public User get(int id) {
        log.info("Get user with id {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("Create user {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("Delete user with id {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("Update {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void setEnabled(int id, boolean enabled) {
        log.info("Set enabled {} with id {}", enabled, id);
        service.enable(id, enabled);
    }
}
