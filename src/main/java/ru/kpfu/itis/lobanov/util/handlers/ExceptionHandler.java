package ru.kpfu.itis.lobanov.util.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";
    public static final String SERVLET_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String SERVLET_REQUEST_URL = "javax.servlet.error.request_url";
    public static final String STATUS_CODE = "statusCode";
    public static final String URI = "uri";
    public static final String MESSAGE = "message";
    public static final String SIMPLE_EXCEPTION_MESSAGE = "simpleMessage";

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Throwable ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        Integer code = (Integer) req.getAttribute(SERVLET_STATUS_CODE);
        String uri = (String) req.getAttribute(SERVLET_REQUEST_URL);
        Throwable throwable = (Throwable) req.getAttribute(SERVLET_EXCEPTION);

        ModelAndView mav = new ModelAndView();
        mav.addObject(STATUS_CODE, code);
        mav.addObject(URI, uri == null ? "" : uri);
        if (throwable != null) mav.addObject(MESSAGE, throwable.getMessage());
        mav.addObject(SIMPLE_EXCEPTION_MESSAGE, ex.getLocalizedMessage());
        mav.setViewName(ServerResources.EXCEPTION_PAGE);
        return mav;
    }
}
