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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String isRemembered = req.getParameter("remember_me");

        resp.setContentType("text/plain");

        if (name.trim().isEmpty()) {
            resp.getWriter().write("emptyName");
            return;
        }
        if (name.trim().length() > 60) {
            resp.getWriter().write("longName");
            return;
        }
        if (lastname.trim().isEmpty()) {
            resp.getWriter().write("emptyLastname");
            return;
        }
        if (lastname.trim().length() > 60) {
            resp.getWriter().write("longLastname");
            return;
        }
        if (email.isEmpty()) {
            resp.getWriter().write("emptyEmail");
            return;
        }
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            resp.getWriter().write("invalidEmail");
            return;
        }
        if (nickname.trim().isEmpty()) {
            resp.getWriter().write("emptyNickname");
            return;
        }
        if (nickname.trim().length() < 5) {
            resp.getWriter().write("shortNickname");
            return;
        }
        if (nickname.trim().length() > 60) {
            resp.getWriter().write("longNickname");
            return;
        }
        if (password.isEmpty()) {
            resp.getWriter().write("emptyPassword");
            return;
        }
        if (password.length() < 8) {
            resp.getWriter().write("shortPassword");
            return;
        }
        if (!password.matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            resp.getWriter().write("weakPassword");
            return;
        }
        if (!confirmPassword.equals(password)) {
            resp.getWriter().write("invalidConfirmPassword");
            return;
        }
        if (!userService.isEmailUnique(email)) {
            resp.getWriter().write("nonUniqueEmail");
            return;
        }
        if (!userService.isNicknameUnique(nickname)) {
            resp.getWriter().write("nonUniqueNickname");
            return;
        }

        userService.save(
                new User(
                        name, lastname, email, nickname, password, null
                )
        );
        UserDto user = userService.get(nickname);
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("currentUser", user);
        if (isRemembered != null && isRemembered.equals("on")) {
            userService.remember(user, req, resp);
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/home");
    }
}
