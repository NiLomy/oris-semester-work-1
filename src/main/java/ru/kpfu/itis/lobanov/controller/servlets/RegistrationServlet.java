package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = ServerResources.REGISTRATION_URL)
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute(ServerResources.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ServerResources.REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.save(req, resp);
        resp.sendRedirect(getServletContext().getContextPath() + ServerResources.HOME_URL);
    }
}
