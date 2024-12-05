package ru.ekaterinakonova.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.User;
import ru.ekaterinakonova.restaurantvoting.to.UserTo;
import ru.ekaterinakonova.restaurantvoting.util.UserUtil;
import ru.ekaterinakonova.restaurantvoting.web.AbstractControllerTest;
import ru.ekaterinakonova.restaurantvoting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJson;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.*;
import static ru.ekaterinakonova.restaurantvoting.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.ekaterinakonova.restaurantvoting.web.user.AdminRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {
    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void getAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL)
                        .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserTo createdTo = new UserTo(null, "newFirstname", "newLastname", "newemail@ya.ru", "newPassword");
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(USER))
                        .content(JsonUtil.writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);
        User created = UserUtil.createNewFromTo(createdTo);
        created.setId(returned.getId());
        assertMatch(returned, created);
        assertMatch(userService.getByEmail("newemail@ya.ru"), created);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("UpdatedEmail@ya.ru");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(USER))
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getByEmail("UpdatedEmail@ya.ru"), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, null, null, "password");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(USER))
                        .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newFirstname", "newLastname", "admin@email.com", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(USER))
                        .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isConflict())
                .andDo(print());
    }
}
