package ru.kpfu.itis.lobanov.controller.servlets;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = ServerResources.ANOTHER_PROFILE_URL)
public class AnotherUserProfileServlet extends HttpServlet {
    private UserServiceImpl userService;
    private PostServiceImpl postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute(ServerResources.USER_SERVICE);
        postService = (PostServiceImpl) getServletContext().getAttribute(ServerResources.POST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter(ServerResources.ANOTHER_USER);
        UserDto anotherUserDto = userService.get(nickname);
        req.setAttribute(ServerResources.ANOTHER_USER, anotherUserDto);

        HttpSession httpSession = req.getSession();
        UserDto currentUserDto = (UserDto) httpSession.getAttribute(ServerResources.CURRENT_USER);

        List<PostDto> posts = postService.getAllFromUser(nickname);

        if (currentUserDto != null && anotherUserDto.getLogin().equals(currentUserDto.getLogin())) {
            req.setAttribute(ServerResources.CURRENT_USER_POSTS, posts);
            req.getRequestDispatcher(ServerResources.PROFILE_PAGE).forward(req, resp);
        } else {
            req.setAttribute(ServerResources.ANOTHER_USER_POSTS, posts);
            req.getRequestDispatcher(ServerResources.ANOTHER_USER_PROFILE_PAGE).forward(req, resp);
        }
    }
}
