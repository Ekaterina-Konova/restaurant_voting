package ru.ekaterinakonova.restaurantvoting.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ekaterinakonova.restaurantvoting.model.Role;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readListFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final User USER = new User(USER_ID, "User", "User", "user@email.com", "user_password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "Admin", "admin@email.com", "admin_password", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user,String password) {
        return JsonUtil.writeAdditionProps(user,"password",password);
    }
}

