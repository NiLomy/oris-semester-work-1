package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.impl.MessageDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;

import java.util.List;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    public static final MessageDaoImpl messageDao = new MessageDaoImpl();
    @Override
    public MessageDto get(int id) {
        Message message = messageDao.get(id);
        if (message == null) return null;
        return new MessageDto(
                message.getAuthor(),
                message.getContent(),
                message.getPost(),
                message.getDate(),
                message.getLikes()
        );
    }

    @Override
    public List<MessageDto> getAllFromPost(String post) {
        return messageDao.getAllFromPost(post).stream().map(
                message -> new MessageDto(
                        message.getAuthor(),
                        message.getContent(),
                        message.getPost(),
                        message.getDate(),
                        message.getLikes()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getAll() {
        return messageDao.getAll().stream().map(
                message -> new MessageDto(
                        message.getAuthor(),
                        message.getContent(),
                        message.getPost(),
                        message.getDate(),
                        message.getLikes()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public void save(Message message) {
        messageDao.save(message);
    }
}
