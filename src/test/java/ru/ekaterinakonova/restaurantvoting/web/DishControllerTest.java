package ru.ekaterinakonova.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Dish;
import ru.ekaterinakonova.restaurantvoting.to.DishTo;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJson;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.DishTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.ADMIN;
import static ru.ekaterinakonova.restaurantvoting.data.DishTestData.contentJson;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.DATA_ERROR;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.VALIDATION_ERROR;

public class DishControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishController.REST_URL + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_3, DISH_2, DISH_4, DISH_5, DISH_1, DISH_6));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_ID_1 + '/' + DISH_ID_1)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_1));
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_ID_1 + '/' + DISH_ID_3)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Dish expected = new Dish(null, "New dish", 10000);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + MENU_ID_1)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Dish returned = readFromJson(action, Dish.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(dishService.getAll(),DISH_2, DISH_3, DISH_4, DISH_5, DISH_1, DISH_6, expected);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ID_4 + '/' + DISH_ID_6)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(dishService.getAll(), DISH_2, DISH_3, DISH_4, DISH_5, DISH_1);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MENU_ID_1 + '/' + DISH_ID_3)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = new Dish(DISH_1);
        updated.setName("UpdatedName");
        updated.setCost(100);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MENU_ID_1 + '/' + DISH_ID_1)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(dishService.get(DISH_ID_1, MENU_ID_1), updated);
    }

    @Test
    void findByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "by?date=" + LocalDate.of(2019, 6, 11))
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_3, DISH_4, DISH_5, DISH_6));
    }

    @Test
    void findByMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MENU_ID_3)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_3, DISH_4, DISH_5));
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "", 200);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + MENU_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_1);
        invalid.setName("");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MENU_ID_1 + '/' + DISH_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        DishTo invalid = new DishTo(DISH_ID_3, "McSteak", 6000);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MENU_ID_3 + '/' + DISH_ID_3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isContinue())
                .andExpect(errorType(DATA_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        DishTo invalid = new DishTo(null, "McSteak", 6000);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + MENU_ID_3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isContinue())
                .andExpect(errorType(DATA_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(DISH_ID_1, "<script>alert(123)</script>", 200);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MENU_ID_1 + '/' + DISH_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}
