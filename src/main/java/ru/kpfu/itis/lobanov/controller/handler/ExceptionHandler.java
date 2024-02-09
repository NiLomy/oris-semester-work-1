package ru.kpfu.itis.lobanov.controller.handler;

import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ServerResources.EXCEPTION_HANDLER_URL)
public class ExceptionHandler extends HttpServlet {
    public static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";
    public static final String SERVLET_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String SERVLET_REQUEST_URL = "javax.servlet.error.request_url";
    public static final String STATUS_CODE = "statusCode";
    public static final String URI = "uri";
    public static final String MESSAGE = "message";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleException(req, resp);
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer code = (Integer) req.getAttribute(SERVLET_STATUS_CODE);
        String uri = (String) req.getAttribute(SERVLET_REQUEST_URL);
        Throwable throwable = (Throwable) req.getAttribute(SERVLET_EXCEPTION);

        req.setAttribute(STATUS_CODE, code);
        req.setAttribute(URI, uri == null ? "" : uri);
        if (code == 500) {
            req.setAttribute(MESSAGE, throwable.getMessage());
        }

        req.getRequestDispatcher(ServerResources.EXCEPTION_PAGE).forward(req, resp);
    }
}
