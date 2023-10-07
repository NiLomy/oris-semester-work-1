package ru.kpfu.itis.lobanov.controller.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/handle")
public class ExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleException(req, resp);
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String uri = (String) req.getAttribute("javax.servlet.error.request_url");

        req.setAttribute("statusCode", code);
        req.setAttribute("uri", uri == null ? "" : uri);
        if (code == 500) {
            req.setAttribute("message", throwable.getMessage());
        }

        req.getRequestDispatcher("WEB-INF/view/exception.ftl").forward(req, resp);
    }
}
