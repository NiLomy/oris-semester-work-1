package ru.kpfu.itis.lobanov.controller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(ServerResources.EXCEPTION_HANDLER_URL)
public class ExceptionHandlerController {
    public static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";
    public static final String SERVLET_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String SERVLET_REQUEST_URL = "javax.servlet.error.request_url";
    public static final String STATUS_CODE = "statusCode";
    public static final String URI = "uri";
    public static final String MESSAGE = "message";

    @GetMapping
    public String handleException(HttpServletRequest req) {
        Integer code = (Integer) req.getAttribute(SERVLET_STATUS_CODE);
        String uri = (String) req.getAttribute(SERVLET_REQUEST_URL);
        Throwable throwable = (Throwable) req.getAttribute(SERVLET_EXCEPTION);

        req.setAttribute(STATUS_CODE, code);
        req.setAttribute(URI, uri == null ? "" : uri);
        req.setAttribute(MESSAGE, throwable.getMessage());

        return ServerResources.EXCEPTION_PAGE;
    }
}
