package ru.ekaterinakonova.restaurantvoting.web;


import lombok.Getter;
import ru.ekaterinakonova.restaurantvoting.model.User;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public void update(User newUser) {
        user = newUser;
    }

    @Override
    public String toString() {
        return user.toString();
    }

}
