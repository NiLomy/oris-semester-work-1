package ru.kpfu.itis.lobanov.model.service.impl;

import com.google.gson.Gson;
import ru.kpfu.itis.lobanov.model.dao.MessageDao;
import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.MessageDaoImpl;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.MessageLikeService;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    public final MessageDao messageDao;
    private final UserDao userDao;
    private final MessageLikeService messageLikeService;

    public MessageServiceImpl(MessageDao messageDao, UserDao userDao, MessageLikeService messageLikeService) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.messageLikeService = messageLikeService;
    }

    @Override
    public MessageDto get(int id) {
        Message message = messageDao.get(id);
        if (message == null) return null;
        User user = userDao.get(message.getAuthorId());
        return new MessageDto(
                message.getId(),
                user.getLogin(),
                message.getContent(),
                message.getPost(),
                message.getDate(),
                message.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public MessageDto get(String author, String content, String post, Timestamp date, int likes) {
        User user = userDao.get(author);
        Message message = messageDao.get(user.getId(), content, post, date, likes);
        if (message == null) return null;
        return new MessageDto(
                message.getId(),
                user.getLogin(),
                message.getContent(),
                message.getPost(),
                message.getDate(),
                message.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public List<MessageDto> getAllFromPost(String post) {
        return messageDao.getAllFromPost(post).stream().map(
                message -> {
                    User user = userDao.get(message.getAuthorId());
                    return new MessageDto(
                        message.getId(),
                        user.getLogin(),
                        message.getContent(),
                        message.getPost(),
                        message.getDate(),
                        message.getLikes(),
                        user.getImageUrl()
                    );
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getAll() {
        return messageDao.getAll().stream().map(
                message -> {
                    User user = userDao.get(message.getAuthorId());
                    return new MessageDto(
                        message.getId(),
                        user.getLogin(),
                        message.getContent(),
                        message.getPost(),
                        message.getDate(),
                        message.getLikes(),
                        user.getImageUrl()
                    );
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            messageDao.save(new Message(
                    user.getId(), newMessage, postDto.getName(), date, 0
            ));
            MessageDto messageDto = get(user.getLogin(), newMessage, postDto.getName(), date, 0);
            resp.setContentType("application/json");
            String json = new Gson().toJson(messageDto);
            json = json.replace("id", "messageID");
            json = json.replace("content", "messageContent");
            resp.getWriter().write(json);
        }
    }

    @Override
    public void updateLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int messageId = Integer.parseInt(req.getParameter("messageId"));
        MessageDto message = get(messageId);

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
        messageDao.updateLikes(messageId, likes);
        message.setLikes(likes);

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(likes));
    }

    @Override
    public UserDto getMostFrequentUser() {
        User user = userDao.get(messageDao.getMostFrequentUserId());
        if (user == null) return null;
        return new UserDto(
                user.getName(),
                user.getLastname(),
                user.getLogin(),
                user.getEmail(),
                user.getImageUrl(),
                user.getAboutMe()
        );
    }

    private Timestamp getDate() {
        String[] dateInput = ZonedDateTime.now().toString().split("T");
        String[] timeInput = dateInput[1].split("\\.");
        String stringDate = dateInput[0];
        String stringTime = timeInput[0];

        return Timestamp.valueOf(stringDate + " " + stringTime);
    }
}
