package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/password-change")
public class PasswordChangeServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String repeatPassword = req.getParameter("repeatPassword");

        HttpSession httpSession = req.getSession();
        String nickname = (String) httpSession.getAttribute("userName");

        resp.setContentType("text/plain");
        if (!userService.isPasswordMatches(nickname, currentPassword)) {
            resp.getWriter().write("You entered incorrect password");
        } else {
            if (!newPassword.equals(repeatPassword)) {
                resp.getWriter().write("Passwords doesn't match");
            } else {
                if (userService.updatePassword(nickname, newPassword)) {
                    resp.getWriter().write("Your password was successfully changed!");
                } else {
                    resp.getWriter().write("OOOOPS!");
                }
            }
        }
    }
}
