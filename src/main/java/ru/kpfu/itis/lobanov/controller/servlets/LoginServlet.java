package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.PasswordUtil;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = PasswordUtil.encrypt(req.getParameter("password"));
//        String password = req.getParameter("password");
        String isRemembered = req.getParameter("remember_me");

        UserDto userDto = userService.get(login, password);
        if (userDto == null) {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        } else {
            String email = userService.getEmail(login, password);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("userName", login);
            httpSession.setAttribute("email", email);
            httpSession.setAttribute("currentUser", userDto);
            if (isRemembered != null && isRemembered.equals("on")) {
                userService.auth(userDto, req, resp);
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        }
    }
}
