package ru.ekaterinakonova.restaurantvoting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.ekaterinakonova.restaurantvoting.util.SecurityUtil;
import ru.ekaterinakonova.restaurantvoting.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error("Exception at request" + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ModelAndView modelAndView = new ModelAndView("exception/exception");
        modelAndView.addObject("exception", rootCause);
        modelAndView.addObject("message", rootCause.toString());
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            modelAndView.addObject("user", authorizedUser.getUser());
        }
        return modelAndView;
    }
}
