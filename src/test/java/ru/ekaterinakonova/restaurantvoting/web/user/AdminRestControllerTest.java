package ru.ekaterinakonova.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Role;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType;
import ru.ekaterinakonova.restaurantvoting.web.AbstractControllerTest;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJson;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.*;

class AdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + 1)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + USER.getEmail())
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID)
                        .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        User updated = new User();
        updated.setLastName("UpdatedLastName");
        updated.setFirstName("UpdatedFirstName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(ADMIN))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(userService.get(USER_ID), updated);

    }

    @Test
    void createWithLocation() throws Exception {
        User expected = new User(null, "New", "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN, USER));
    }

    @Test
    void createInvalid() throws Exception {
        User created = new User(null, "", "", "new@gmail.com", "password", Role.ROLE_USER);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(ADMIN))
                        .content(jsonWithPassword(created, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        User updated = new User(USER);
        updated.setLastName("");
        updated.setFirstName("");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(ADMIN))
                        .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        User updated = new User(USER);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(ADMIN))
                        .content(jsonWithPassword(updated, "password")))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.DATA_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        User created = new User(null, "newUser", "newUser", "admin@email.com", "password", Role.ROLE_USER);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(ADMIN))
                        .content(jsonWithPassword(created, "password")))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.DATA_ERROR));
    }
}
