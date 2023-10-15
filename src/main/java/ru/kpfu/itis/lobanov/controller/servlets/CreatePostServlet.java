package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.ZonedDateTime;

@WebServlet(urlPatterns = "/create-post")
public class CreatePostServlet extends HttpServlet {
    private PostServiceImpl postService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/create-post.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postName = req.getParameter("postName");
        String postCategory = req.getParameter("postCategory");
        String postText = req.getParameter("postText");
        String stringDate = ZonedDateTime.now().toString().substring(0, 10);
        Date date = Date.valueOf(stringDate);

        if (postName == null || postCategory == null || postText == null) {
            resp.setContentType("text/plain");
            resp.getWriter().write("You entered wrong data");
        } else {
            HttpSession httpSession = req.getSession();
            UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
            UserDaoImpl userDao = new UserDaoImpl();
            User user = userDao.get(userDto.getLogin());

            postService.save(
                    new Post(
                            postName,
                            postCategory,
                            postText,
                            user.getId(),
                            date,
                            0
                    )
            );
//            httpSession.setAttribute("currentPost", new PostDto(
//                    postName,
//                    postCategory,
//                    postText,
//                    userDto.getLogin(),
//                    date,
//                    0
//            ));
            httpSession.setAttribute("postName", postName);

            resp.sendRedirect(getServletContext().getContextPath() + "/post?postName=" + postName + "&postAuthor=" + user.getLogin());
        }
    }
}
