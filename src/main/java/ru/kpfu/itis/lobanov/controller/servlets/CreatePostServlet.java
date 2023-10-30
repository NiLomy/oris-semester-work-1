package ru.kpfu.itis.lobanov.controller.servlets;

import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.PostService;
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
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@WebServlet(urlPatterns = "/create-post")
public class CreatePostServlet extends HttpServlet {
    private PostService postService;

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

        Timestamp date = getDate();

        resp.setContentType("text/plain");
        if (postName.isEmpty()) {
            resp.getWriter().write("emptyPostName");
            return;
        }
        if (postName.length() < 5) {
            resp.getWriter().write("shortPostName");
            return;
        }
        if (postName.length() > 100) {
            resp.getWriter().write("longPostName");
            return;
        }
        if (postCategory.isEmpty()) {
            resp.getWriter().write("emptyPostCategory");
            return;
        }
        if (postText.isEmpty()) {
            resp.getWriter().write("emptyPostText");
            return;
        }
        HttpSession httpSession = req.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");

        if (!postService.isPostUnique(userDto.getLogin(), postName)) {
            resp.getWriter().write("postAlreadyExist");
            return;
        }

        UserDao userDao = new UserDaoImpl();
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

        resp.sendRedirect(getServletContext().getContextPath() + "/post?postName=" + postName + "&postAuthor=" + user.getLogin());
    }

    private Timestamp getDate() {
        String[] dateInput = ZonedDateTime.now().toString().split("T");
        String[] timeInput = dateInput[1].split("\\.");
        String stringDate = dateInput[0];
        String stringTime = timeInput[0];

        return Timestamp.valueOf(stringDate + " " + stringTime);
    }
}
