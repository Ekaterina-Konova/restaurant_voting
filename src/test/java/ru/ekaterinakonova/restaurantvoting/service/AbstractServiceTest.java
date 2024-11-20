package ru.ekaterinakonova.restaurantvoting.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.ekaterinakonova.restaurantvoting.AbstractTest;
import ru.ekaterinakonova.restaurantvoting.TimingExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.getRootCause;

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest extends AbstractTest {
    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
