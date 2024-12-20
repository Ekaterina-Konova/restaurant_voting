package ru.ekaterinakonova.restaurantvoting.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.ekaterinakonova.restaurantvoting.model.Role;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.to.UserTo;

public class UserUtil {
    private UserUtil() {
    }

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getFirstName(), userTo.getLastName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
