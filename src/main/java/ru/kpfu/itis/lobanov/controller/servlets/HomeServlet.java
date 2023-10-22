package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.impl.MessageServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Comparator;

@WebServlet(name = "homeServlet", urlPatterns = "/")
public class HomeServlet extends HttpServlet {
    private UserServiceImpl userService;
    private MessageServiceImpl messageService;
    private PostServiceImpl postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
        messageService = (MessageServiceImpl) getServletContext().getAttribute("messageService");
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    HttpSession httpSession = req.getSession();
                    UserDto user = userService.get(c.getValue());
                    httpSession.setAttribute("currentUser", user);
                    break;
                }
            }
        }

        UserDto userDto = messageService.getMostFrequentUser();
        PostDto postDto = postService.getAll().stream().max(Comparator.comparingInt(PostDto::getLikes)).get();
        req.setAttribute("mostPopularPost", postDto);
        req.setAttribute("mostActiveUser", userDto);
        req.getRequestDispatcher("WEB-INF/view/home.ftl").forward(req, resp);
    }
}
