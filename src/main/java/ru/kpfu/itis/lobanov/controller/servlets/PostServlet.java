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
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
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

@WebServlet(urlPatterns = ServerResources.POST_URL)
public class PostServlet extends HttpServlet {
    private PostService postService;
    private MessageService messageService;
    private PostLikeService postLikeService;
    private MessageLikeService messageLikeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postService = (PostServiceImpl) getServletContext().getAttribute(ServerResources.POST_SERVICE);
        messageService = (MessageServiceImpl) getServletContext().getAttribute(ServerResources.MESSAGE_SERVICE);
        postLikeService = (PostLikeServiceImpl) getServletContext().getAttribute(ServerResources.POST_LIKE_SERVICE);
        messageLikeService = (MessageLikeServiceImpl) getServletContext().getAttribute(ServerResources.MESSAGE_LIKE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postName = req.getParameter(ServerResources.POST_NAME);
        String postAuthor = req.getParameter(ServerResources.POST_AUTHOR);
        PostDto postDto = postService.get(postName, postAuthor);

        List<MessageDto> messages = messageService.getAllFromPost(postName);
        messages.sort(Comparator.comparing(MessageDto::getDate));
        req.setAttribute(ServerResources.MESSAGES, messages);

        HttpSession httpSession = req.getSession();
        Object currentUser = httpSession.getAttribute(ServerResources.CURRENT_USER);

        if (currentUser != null) {
            List<PostDto> posts = postService.getAllFavouriteFromUser(((UserDto) currentUser).getLogin());
            boolean isFavourite = posts.stream().anyMatch(post -> post.getName().equals(postName));

            if (isFavourite) req.setAttribute(ServerResources.IS_FAVOURITE, true);
        }

        httpSession.setAttribute(ServerResources.CURRENT_POST, postDto);
        req.getRequestDispatcher(ServerResources.POST_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ServerResources.ACTION_KEY);

        if (action.equals(ServerResources.SEND_MESSAGE_ACTION)) messageService.save(req, resp);
        if (action.equals(ServerResources.PRESS_LIKE_ACTION)) postService.updateLikes(req, resp);
        if (action.equals(ServerResources.PRESS_MESSAGE_LIKE_ACTION)) messageService.updateLikes(req, resp);
        if (action.equals(ServerResources.PRESS_FAVOURITE_ACTION)) postService.saveToFavourites(req, resp);
        if (action.equals(ServerResources.PRESS_UNFAVOURITE_ACTION)) postService.removeFromFavourites(req, resp);
    }
}
