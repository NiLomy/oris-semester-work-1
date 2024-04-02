package ru.kpfu.itis.lobanov.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/handle")
public class ErrorHandlingController {
    public static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";
    public static final String SERVLET_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String SERVLET_REQUEST_URL = "javax.servlet.error.request_url";
    public static final String STATUS_CODE = "statusCode";
    public static final String URI = "uri";
    public static final String MESSAGE = "message";

    @GetMapping
    public ModelAndView handleException(HttpServletRequest req, Throwable ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        Integer code = (Integer) req.getAttribute(SERVLET_STATUS_CODE);
        String uri = (String) req.getAttribute(SERVLET_REQUEST_URL);
        Throwable throwable = (Throwable) req.getAttribute(SERVLET_EXCEPTION);

        if (code == HttpStatus.NOT_FOUND.value()) return new ModelAndView("pageNotFound");

        ModelAndView mav = new ModelAndView();
        mav.addObject(STATUS_CODE, code);
        mav.addObject(URI, uri == null ? "" : uri);
        if (throwable != null) mav.addObject(MESSAGE, throwable.getMessage());
        mav.setViewName(ServerResources.EXCEPTION_PAGE);
        return mav;
    }
}
