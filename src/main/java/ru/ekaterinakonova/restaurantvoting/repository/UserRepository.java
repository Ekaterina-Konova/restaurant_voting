package ru.ekaterinakonova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ekaterinakonova.restaurantvoting.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
