//package ru.kpfu.itis.lobanov.controller.servlets;
//
//import ru.kpfu.itis.lobanov.model.service.PostService;
//import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
//import ru.kpfu.itis.lobanov.util.constants.ServerResources;
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
//
//@WebServlet(urlPatterns = ServerResources.CREATE_POST_URL)
//public class CreatePostServlet extends HttpServlet {
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
//        req.getRequestDispatcher(ServerResources.CREATE_POST_PAGE).forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String postName = req.getParameter(ServerResources.POST_NAME);
//        String postCategory = req.getParameter(ServerResources.POST_CATEGORY);
//        String postText = req.getParameter(ServerResources.POST_TEXT);
//
//        if (!postService.isPostValid(postName, postCategory, postText, req, resp)) return;
//
//        HttpSession httpSession = req.getSession();
//        UserDto userDto = (UserDto) httpSession.getAttribute(ServerResources.CURRENT_USER);
//
//        if (!postService.isPostUnique(userDto.getLogin(), postName)) {
//            resp.getWriter().write(ServerResources.POST_ALREADY_EXISTS);
//            return;
//        }
//
//        postService.save(postName, postCategory, postText, userDto.getLogin());
//
//        resp.sendRedirect(getServletContext().getContextPath() + String.format(ServerResources.POST_OF_AUTHOR_URL, postName, userDto.getLogin()));
//    }
//}
