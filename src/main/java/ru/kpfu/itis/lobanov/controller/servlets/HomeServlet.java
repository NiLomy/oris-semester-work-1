package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.service.UserService;
import ru.kpfu.itis.lobanov.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

//    for using db in code

//    private UserServiceImpl userService;
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/home.ftl").forward(req, resp);
    }
}
