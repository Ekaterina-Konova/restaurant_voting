package ru.ekaterinakonova.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ekaterinakonova.restaurantvoting.model.Vote;
import ru.ekaterinakonova.restaurantvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ekaterinakonova.restaurantvoting.data.MenuTestData.*;
import static ru.ekaterinakonova.restaurantvoting.data.UserTestData.USER_ID;
import static ru.ekaterinakonova.restaurantvoting.data.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void create() throws Exception {
        Vote newVote = new Vote(null, LocalDate.now());
        Vote created = service.create(new Vote(newVote), USER_ID, MENU_ID_1);
        newVote.setId(created.getId());
        assertMatch(created, newVote);
        assertMatch(service.getAll(), VOTE_1, VOTE_2, VOTE_3, newVote);
    }

    @Test
    void delete() throws Exception {
        service.delete(VOTE_ID_1, USER_ID, MENU_ID_2);
        assertMatch(service.getAll(), VOTE_2, VOTE_3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1, 1, 1));
    }

    @Test
    void get() throws Exception {
        Vote vote = service.get(VOTE_ID_1, USER_ID, MENU_ID_2);
        assertMatch(vote, VOTE_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1, 1, 1));
    }

    @Test
    void getForUserAndDate() throws Exception {
        Vote vote = service.getForUserAndDate(USER_ID, LocalDate.of(2019, 4, 20));
        assertMatch(vote, VOTE_1);
    }

    @Test
    void update() throws Exception {
        Vote updated = new Vote(VOTE_1);
        updated.setDate(LocalDate.of(3000, 6, 11));
        service.update(new Vote(updated), USER_ID, MENU_ID_2);
        assertMatch(service.get(VOTE_ID_1, USER_ID, MENU_ID_2), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Vote> all = service.getAll();
        assertMatch(all, VOTE_1, VOTE_2, VOTE_3);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Vote(null, null), USER_ID, MENU_ID_2), ConstraintViolationException.class);
    }
}
