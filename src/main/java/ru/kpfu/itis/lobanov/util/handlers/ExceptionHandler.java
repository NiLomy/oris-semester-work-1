package ru.kpfu.itis.lobanov.util.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String SIMPLE_EXCEPTION_MESSAGE = "simpleMessage";

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Throwable ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject(SIMPLE_EXCEPTION_MESSAGE, ex.getLocalizedMessage());
        mav.setViewName(ServerResources.EXCEPTION_PAGE);
        return mav;
    }
}
