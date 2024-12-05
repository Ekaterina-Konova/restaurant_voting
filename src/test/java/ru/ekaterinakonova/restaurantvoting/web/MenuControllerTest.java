package ru.ekaterinakonova.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Menu;
import ru.ekaterinakonova.restaurantvoting.to.MenuTo;
import ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJson;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.DishTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.contentJson;
import static ru.ekaterinakonova.restaurantvoting.data.RestaurantTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.ADMIN;
import static ru.ekaterinakonova.restaurantvoting.util.MenuUtil.menuFromTo;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.DATA_ERROR;

public class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_1, MENU_2, MENU_3, MENU_4, MENU_5));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_1 + '/' + MENU_1)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_1));
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_2 + '/' + 0)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        MenuTo expected = new MenuTo(null, LocalDate.now(), DISH_1, DISH_3);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID_2)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Menu returned = readFromJson(action, Menu.class);
        expected.setId(returned.getId());
        assertMatch(returned, menuFromTo(expected));
        assertMatch(menuService.getAll(), MENU_1, MENU_2, MENU_3, MENU_4, MENU_5, menuFromTo(expected));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_1 + '/' + MENU_ID_1)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuService.getAll(), MENU_2, MENU_3, MENU_4, MENU_5);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_2 + '/' + 0)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        MenuTo updated = new MenuTo(MENU_ID_1, LocalDate.now(), DISH_1, DISH_3);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1 + '/' + MENU_ID_1)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(menuService.get(MENU_ID_1, RESTAURANT_ID_1), menuFromTo(updated));
    }

    @Test
    void findByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "byDate?date=" + LocalDate.of(2019, 6, 11))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_3, MENU_4));
    }

    @Test
    void findByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "byRestaurant?restaurant_id" + RESTAURANT_ID_2)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MENU_2, MENU_3));
    }

    @Test
    void createInvalid() throws Exception {
        MenuTo invalid = new MenuTo(null, null);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID_3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        MenuTo invalid = new MenuTo(MENU_ID_2, null);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_2 + '/' + MENU_ID_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicated() throws Exception {
        MenuTo invalid = new MenuTo(MENU_ID_2, LocalDate.of(2019, 6, 11));
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_2 + '/' + MENU_ID_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        MenuTo invalid = new MenuTo(null, LocalDate.of(2019, 6, 11));
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID_3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(DATA_ERROR));
    }
}
