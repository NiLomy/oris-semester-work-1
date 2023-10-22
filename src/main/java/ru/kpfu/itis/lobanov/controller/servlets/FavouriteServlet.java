package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
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

@WebServlet(urlPatterns = "/favourite")
public class FavouriteServlet extends HttpServlet {
    private PostServiceImpl postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("currentUser");
        List<PostDto> posts = postService.getAllFavouriteFromUser(userDto.getLogin());
        posts.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        req.setAttribute("favouritePosts", posts);
        req.getRequestDispatcher("WEB-INF/view/favourite.ftl").forward(req, resp);
    }
}
