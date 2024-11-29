package ru.ekaterinakonova.restaurantvoting.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ekaterinakonova.restaurantvoting.web.AuthorizedUser;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser authorizedUser = safeGet();
        requireNonNull(authorizedUser, "No authorized user found");
        return authorizedUser;
    }

    public static int authUserId() {
        return get().getUser().getId();
    }
}
