//package ru.kpfu.itis.lobanov.controller.servlets;
//
//import ru.kpfu.itis.lobanov.model.service.PostService;
//import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
//import ru.kpfu.itis.lobanov.util.dto.PostDto;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(urlPatterns = ServerResources.POSTS_URL)
//public class PostsServlet extends HttpServlet {
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
//        List<PostDto> posts = postService.getAll();
//        req.setAttribute(ServerResources.POSTS, posts);
//        req.getRequestDispatcher(ServerResources.POSTS_PAGE).forward(req, resp);
//    }
//}
