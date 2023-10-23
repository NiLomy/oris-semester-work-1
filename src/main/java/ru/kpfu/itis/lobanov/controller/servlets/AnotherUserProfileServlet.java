package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.UserServiceImpl;
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

@WebServlet(urlPatterns = "/another-profile")
public class AnotherUserProfileServlet extends HttpServlet {
    private UserServiceImpl userService;
    private PostServiceImpl postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("anotherUser");
        UserDto anotherUserDto = userService.get(nickname);
        req.setAttribute("anotherUser", anotherUserDto);

        HttpSession httpSession = req.getSession();
        UserDto currentUserDto = (UserDto) httpSession.getAttribute("currentUser");

        List<PostDto> posts = postService.getAllFromUser(nickname);
        posts.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        if (currentUserDto != null && anotherUserDto.getLogin().equals(currentUserDto.getLogin())) {
            req.setAttribute("currentUserPosts", posts);
            req.getRequestDispatcher("WEB-INF/view/profile.ftl").forward(req, resp);
        } else {
            req.setAttribute("anotherUserPosts", posts);
            req.getRequestDispatcher("WEB-INF/view/another-user-profile.ftl").forward(req, resp);
        }
    }

}
