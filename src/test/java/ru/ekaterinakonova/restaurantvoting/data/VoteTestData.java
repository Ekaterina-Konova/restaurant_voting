package ru.ekaterinakonova.restaurantvoting.data;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ekaterinakonova.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.TestUtil.readListFromJsonMvcResult;
import static ru.ekaterinakonova.restaurantvoting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE_ID_1 = START_SEQ + 16;
    public static final int VOTE_ID_2 = START_SEQ + 17;
    public static final int VOTE_ID_3 = START_SEQ + 18;

    public static final Vote VOTE_1 = new Vote(VOTE_ID_1, LocalDate.of(2019, 4, 20));
    public static final Vote VOTE_2 = new Vote(VOTE_ID_2, LocalDate.of(2019, 4, 20));
    public static final Vote VOTE_3 = new Vote(VOTE_ID_3, LocalDate.of(2019, 6, 11));

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Vote.class), expected);
    }
}

