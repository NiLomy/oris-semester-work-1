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

@WebServlet(name = "editProfileServlet", urlPatterns = "/edit_profile")
public class EditProfileServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/edit_profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newLogin = req.getParameter("nickname");
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");

        HttpSession httpSession = req.getSession();
        String oldLogin = (String) httpSession.getAttribute("userName");
        User user = new User(name, lastName, email, newLogin);

        if (userService.update(user, oldLogin)) {
            UserDto userDto = userService.get(newLogin);
            httpSession.setAttribute("userName", userDto.getLogin());
            httpSession.setAttribute("currentUser", userDto);
            resp.setContentType("text/plain");
            resp.getWriter().write("Your data was successfully changed!");
//            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
            // makeToast
        } else {
            // makeToast
            resp.sendRedirect(getServletContext().getContextPath() + "/edit_profile");
        }
    }
}
