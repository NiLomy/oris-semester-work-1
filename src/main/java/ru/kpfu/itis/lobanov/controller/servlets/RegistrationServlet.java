package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "registrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/registration.ftl").forward(req, resp);
        HttpSession httpSession = req.getSession();
        httpSession.invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String isRemembered = req.getParameter("remember_me");

        if (!userService.isEmailUnique(email)) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("isEmailAlreadyExists", "true");
            resp.sendRedirect(getServletContext().getContextPath() + "/registration");
            return;
        }
        UserDto userDto = userService.get(login, password);
        if (userDto == null) {
            userService.save(
                    new User(
                            name, lastname, email, login, password
                    )
            );
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("userName", login);
            if (isRemembered != null && isRemembered.equals("on")) {
                UserDto user = userService.get(login, password);
                userService.auth(user, req, resp);
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("isAlreadyRegistered", "true");
        }
    }
}
