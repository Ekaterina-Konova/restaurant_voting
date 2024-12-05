package ru.ekaterinakonova.restaurantvoting.web;

import lombok.Getter;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.to.UserTo;
import ru.ekaterinakonova.restaurantvoting.util.UserUtil;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        userTo = newTo;

    }

    @Override
    public String toString() {
        return userTo.toString();
    }

}
