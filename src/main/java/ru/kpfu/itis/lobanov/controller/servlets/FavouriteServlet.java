//package ru.kpfu.itis.lobanov.controller.servlets;
//
//import ru.kpfu.itis.lobanov.model.service.PostService;
//import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
//import ru.kpfu.itis.lobanov.util.dto.PostDto;
//import ru.kpfu.itis.lobanov.util.dto.UserDto;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(urlPatterns = ServerResources.FAVOURITE_URL)
//public class FavouriteServlet extends HttpServlet {
//    private PostService postService;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        postService = (PostServiceImpl) getServletContext().getAttribute(ServerResources.POST_SERVICE);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        UserDto userDto = (UserDto) session.getAttribute(ServerResources.CURRENT_USER);
//
//        List<PostDto> posts = postService.getAllFavouriteFromUser(userDto.getLogin());
//        req.setAttribute(ServerResources.FAVOURITE_POSTS, posts);
//        req.getRequestDispatcher(ServerResources.FAVOURITE_PAGE).forward(req, resp);
//    }
//}
