package ru.ekaterinakonova.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.userHttpBasic;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.MENU_ID_1;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.ADMIN;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.USER;
import static ru.ekaterinakonova.restaurantvoting.data.VoteTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.VoteTestData.contentJson;

public class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URI + '/';

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE_1, VOTE_2, VOTE_3));
    }

    @Test
    void voteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + MENU_ID_1)
                        .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
