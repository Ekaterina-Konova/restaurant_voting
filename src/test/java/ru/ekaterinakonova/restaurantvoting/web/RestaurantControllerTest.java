package ru.ekaterinakonova.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Restaurant;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJson;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.RestaurantTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.ADMIN;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.DATA_ERROR;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.VALIDATION_ERROR;

public class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantController.REST_URI + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_1)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + 0)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant expected = new Restaurant(null, "New Restaurant");
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(restaurantService.getAll(), RESTAURANT_1, RESTAURANT_2, RESTAURANT_3, expected);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID_1)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + 0)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.get(RESTAURANT_ID_1), updated);
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, "");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
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
        Restaurant invalid = new Restaurant(RESTAURANT_1);
        invalid.setName("");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBaic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_ID_1, "McDonals");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1)
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
        Restaurant invalid = new Restaurant(null, "BurgerKing");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isContinue())
                .andExpect(errorType(DATA_ERROR));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_ID_1, "<script>alert(123)</script>");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid))
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }
}
