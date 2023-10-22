package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.MessageDaoImpl;
import ru.kpfu.itis.lobanov.model.dao.impl.PostDaoImpl;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    public final MessageDaoImpl messageDao = new MessageDaoImpl();
    private final UserDao<User> userDao = new UserDaoImpl();

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

    public MessageDto get(String author, String content, String post, Date date, int likes) {
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
    public void save(Message message) {
        messageDao.save(message);
    }

    @Override
    public void updateLikes(int id, int likes) {
        messageDao.updateLikes(id, likes);
    }

    @Override
    public UserDto getMostFrequentUser() {
        User user = userDao.get(messageDao.getMostFrequentUserId());
        return new UserDto(
                user.getName(),
                user.getLastname(),
                user.getLogin(),
                user.getEmail(),
                user.getImageUrl(),
                user.getAboutMe()
        );
    }
}
