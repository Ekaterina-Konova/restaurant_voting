package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.repository.UserRepository;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFound;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserById(String email) {
        String lowerCaseEmail = email.toLowerCase();
        Assert.notNull(lowerCaseEmail, "email must not be null");
        return checkNotFound(repository.getByEmail(lowerCaseEmail), "email=" + email);
    }
}
