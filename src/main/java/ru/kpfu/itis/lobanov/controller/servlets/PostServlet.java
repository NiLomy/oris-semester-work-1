package ru.kpfu.itis.lobanov.controller.servlets;

import com.google.gson.Gson;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.service.impl.MessageServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.PostLikeServiceImpl;
import ru.kpfu.itis.lobanov.model.service.impl.PostServiceImpl;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
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
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;

@WebServlet(urlPatterns = "/post")
public class PostServlet extends HttpServlet {
    private PostServiceImpl postService;
    private MessageServiceImpl messageService;
    private PostLikeServiceImpl postLikeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
        messageService = (MessageServiceImpl) getServletContext().getAttribute("messageService");
        postLikeService = (PostLikeServiceImpl) getServletContext().getAttribute("postLikeService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postName = req.getParameter("postName");
        String postAuthor = req.getParameter("postAuthor");
        PostDto postDto = postService.get(postName, postAuthor);

        List<MessageDto> messages = messageService.getAllFromPost(postName);
        req.setAttribute("messages", messages);

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("currentPost", postDto);

        req.getRequestDispatcher("WEB-INF/view/post.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("sendMessage")) sendMessage(req, resp);
        if (action.equals("pressLike")) pressLike(req, resp);
    }

    private void sendMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newMessage = req.getParameter("newMessage");
        String stringDate = ZonedDateTime.now().toString().substring(0, 10);
        Date date = Date.valueOf(stringDate);

        if (newMessage == null) {
            resp.setContentType("text/plain");
            resp.getWriter().write("You entered wrong data");
        } else {
            HttpSession httpSession = req.getSession();
            PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
            UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
            messageService.save(new Message(
                    userDto.getLogin(), newMessage, postDto.getName(), date, 0
            ));
            MessageDto messageDto = new MessageDto(
                    userDto.getLogin(), newMessage, postDto.getName(), date, 0
            );
            resp.setContentType("application/json");
            String json = new Gson().toJson(messageDto);
            json = json.replace("content", "messageContent");
            resp.getWriter().write(json);
        }
    }

    private void pressLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
        int likes = postDto.getLikes();

        if (postLikeService.isSet(userDto.getLogin(), postDto.getName())) {
            postLikeService.remove(new PostLike(userDto.getLogin(), postDto.getName()));
            likes--;
        } else {
            postLikeService.save(new PostLike(userDto.getLogin(), postDto.getName()));
            likes++;
        }
        postService.updateLikes(postDto.getName(), likes);

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(likes));
    }
}
