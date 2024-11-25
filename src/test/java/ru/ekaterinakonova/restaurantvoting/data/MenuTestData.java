package ru.ekaterinakonova.restaurantvoting.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ekaterinakonova.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readListFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int MENU_ID_1 = START_SEQ + 5;
    public static final int MENU_ID_2 = START_SEQ + 6;
    public static final int MENU_ID_3 = START_SEQ + 7;
    public static final int MENU_ID_4 = START_SEQ + 8;
    public static final int MENU_ID_5 = START_SEQ + 9;

    public static final Menu MENU_1 = new Menu(MENU_ID_1, LocalDate.of(2019, 4, 19));
    public static final Menu MENU_2 = new Menu(MENU_ID_2, LocalDate.of(2019, 4, 20));
    public static final Menu MENU_3 = new Menu(MENU_ID_3, LocalDate.of(2019, 6, 11));
    public static final Menu MENU_4 = new Menu(MENU_ID_4, LocalDate.of(2019, 6, 11));
    public static final Menu MENU_5 = new Menu(MENU_ID_5, LocalDate.of(2019, 6, 12));

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Menu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Menu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Menu.class), expected);
    }
}
