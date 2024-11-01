package ru.ekaterinakonova.restaurantvoting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ekaterinakonova.restaurantvoting.model.Role;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments arg) {
        userRepository.save(new User(1, "User_First", "User_Last", "user@gmail.com", "password", true, Set.of(Role.ROLE_USER)));
        userRepository.save(new User(2, "Admin_First", "Admin_Last", "admin@javaops.ru", "admin", true, Set.of(Role.ROLE_USER, Role.ROLE_ADMIN)));
        System.out.println(userRepository.findAll());
    }
}

