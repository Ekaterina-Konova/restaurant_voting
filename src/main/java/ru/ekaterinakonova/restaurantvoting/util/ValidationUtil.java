package ru.ekaterinakonova.restaurantvoting.util;

import ru.ekaterinakonova.restaurantvoting.model.AbstractBaseEntity;
import ru.ekaterinakonova.restaurantvoting.util.exception.NotFoundException;

public class ValidationUtil {
    private ValidationUtil() {

    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);

    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()){
            throw new IllegalArgumentException(entity + "must be new(id=null)");
        }
    }
    public static Throwable getRootCause(Throwable throwable) {
        Throwable result= throwable;
        Throwable cause;
        while ((cause = result.getCause()) != null &&(result != cause)) {
            result = cause;
        }
        return result;
    }
}
