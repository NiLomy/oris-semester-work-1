package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.model.service.UserService;
import ru.kpfu.itis.lobanov.model.service.impl.MessageServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = ServerResources.HOME_URL)
public class HomeServlet extends HttpServlet {
    private UserService userService;
    private MessageService messageService;
    private PostService postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute(ServerResources.USER_SERVICE);
        messageService = (MessageServiceImpl) getServletContext().getAttribute(ServerResources.MESSAGE_SERVICE);
        postService = (PostServiceImpl) getServletContext().getAttribute(ServerResources.POST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.authUser(req, resp);

        UserDto userDto = messageService.getMostFrequentUser();
        PostDto postDto = postService.getMostPopularPost();

        req.setAttribute(ServerResources.MOST_POPULAR_POST, postDto);
        req.setAttribute(ServerResources.MOST_ACTIVE_USER, userDto);
        req.getRequestDispatcher(ServerResources.HOME_PAGE).forward(req, resp);
    }
}
