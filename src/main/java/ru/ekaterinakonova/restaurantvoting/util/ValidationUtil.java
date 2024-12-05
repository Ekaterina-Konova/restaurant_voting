package ru.ekaterinakonova.restaurantvoting.util;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.ekaterinakonova.restaurantvoting.HasId;
import ru.ekaterinakonova.restaurantvoting.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

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

    public static void checkNew(HasId entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + "must be new(id=null)");
        }
    }

    public static void assureIdConsistent(HasId entity, int id) {
        //      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable result = throwable;
        Throwable cause;
        while ((cause = result.getCause()) != null && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String[] getCauseMessage(Throwable throwable) {
        List<String> fieldErrors = new ArrayList<>();
        if (throwable instanceof BindException) {
            BindException exception = (BindException) throwable;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            errors.forEach(e -> fieldErrors.add(((FieldError) e).getField() + ": " + e.getDefaultMessage()));
        }
        return fieldErrors.toArray(String[]::new);
    }
}
