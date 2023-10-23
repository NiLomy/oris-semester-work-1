package ru.kpfu.itis.lobanov.controller.servlets;

import com.google.gson.Gson;
import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.MessageLikeService;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.model.service.PostLikeService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.model.service.impl.MessageLikeServiceImpl;
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
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "postServlet", urlPatterns = "/post")
public class PostServlet extends HttpServlet {
    private PostService postService;
    private MessageService messageService;
    private PostLikeService postLikeService;
    private MessageLikeService messageLikeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postService = (PostServiceImpl) getServletContext().getAttribute("postService");
        messageService = (MessageServiceImpl) getServletContext().getAttribute("messageService");
        postLikeService = (PostLikeServiceImpl) getServletContext().getAttribute("postLikeService");
        messageLikeService = (MessageLikeServiceImpl) getServletContext().getAttribute("messageLikeService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postName = req.getParameter("postName");
        String postAuthor = req.getParameter("postAuthor");
        PostDto postDto = postService.get(postName, postAuthor);

        List<MessageDto> messages = messageService.getAllFromPost(postName);
        messages.sort(Comparator.comparing(MessageDto::getDate));
        req.setAttribute("messages", messages);

        HttpSession httpSession = req.getSession();
        Object currentUser = httpSession.getAttribute("currentUser");

        if (currentUser != null) {
            List<PostDto> posts = postService.getAllFavouriteFromUser(((UserDto) currentUser).getLogin());
            boolean isFavourite = posts.stream().anyMatch(post -> post.getName().equals(postName));

            if (isFavourite) req.setAttribute("isFavourite", true);
        }

        httpSession.setAttribute("currentPost", postDto);
        req.getRequestDispatcher("WEB-INF/view/post.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("sendMessage")) sendMessage(req, resp);
        if (action.equals("pressLike")) pressPostLike(req, resp);
        if (action.equals("pressMessageLike")) pressMessageLike(req, resp);
        if (action.equals("pressFavourite")) pressFavourite(req, resp);
        if (action.equals("pressUnfavourite")) pressUnfavourite(req, resp);
    }

    private void sendMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newMessage = req.getParameter("newMessage");
        Timestamp date = getDate();

        if (newMessage == null) {
            resp.setContentType("text/plain");
            resp.getWriter().write("You entered wrong data");
        } else {
            HttpSession httpSession = req.getSession();
            PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
            UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
            UserDao userDao = new UserDaoImpl();
            User user = userDao.get(userDto.getLogin());

            messageService.save(new Message(
                    user.getId(), newMessage, postDto.getName(), date, 0
            ));
            MessageDto messageDto = messageService.get(user.getLogin(), newMessage, postDto.getName(), date, 0);
            resp.setContentType("application/json");
            String json = new Gson().toJson(messageDto);
            json = json.replace("id", "messageID");
            json = json.replace("content", "messageContent");
            resp.getWriter().write(json);
        }
    }

    private void pressPostLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        postDto.setLikes(likes);
        httpSession.setAttribute("currentPost", postDto);

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(likes));
    }

    private void pressMessageLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int messageId = Integer.parseInt(req.getParameter("messageId"));
        MessageDto message = messageService.get(messageId);

        HttpSession httpSession = req.getSession();
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
        int likes = message.getLikes();

        if (messageLikeService.isSet(userDto.getLogin(), messageId)) {
            messageLikeService.remove(new MessageLike(userDto.getLogin(), messageId));
            likes--;
        } else {
            messageLikeService.save(new MessageLike(userDto.getLogin(), messageId));
            likes++;
        }
        messageService.updateLikes(messageId, likes);
        message.setLikes(likes);

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(likes));
    }

    private void pressFavourite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");

        postService.saveToFavourites(userDto.getLogin(), postDto.getName(), postDto.getAuthor());
    }

    private void pressUnfavourite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");

        postService.removeFromFavourites(userDto.getLogin(), postDto.getName(), postDto.getAuthor());
    }

    private Timestamp getDate() {
        String[] dateInput = ZonedDateTime.now().toString().split("T");
        String[] timeInput = dateInput[1].split("\\.");
        String stringDate = dateInput[0];
        String stringTime = timeInput[0];

        return Timestamp.valueOf(stringDate + " " + stringTime);
    }
}
