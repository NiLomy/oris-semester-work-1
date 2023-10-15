package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.MessageLikeDao;
import ru.kpfu.itis.lobanov.model.dao.impl.MessageLikeDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.model.service.MessageLikeService;
import ru.kpfu.itis.lobanov.util.dto.MessageLikeDto;

public class MessageLikeServiceImpl implements MessageLikeService {
    private final MessageLikeDao<MessageLike> messageLikeDao = new MessageLikeDaoImpl();

    @Override
    public MessageLikeDto get(int id) {
        MessageLike messageLike = messageLikeDao.get(id);
        return new MessageLikeDto(messageLike.getAuthor(), messageLike.getMessageId());
    }

    @Override
    public boolean isSet(String author, int messageId) {
        MessageLike messageLike = messageLikeDao.get(author, messageId);
        return messageLike != null;
    }

    @Override
    public int getAmountFromPost(int messageId) {
        return messageLikeDao.getAllFromMessage(messageId).size();
    }

    @Override
    public int getAmount() {
        return messageLikeDao.getAll().size();
    }

    @Override
    public void save(MessageLike messageLike) {
        messageLikeDao.save(messageLike);
    }

    @Override
    public void remove(MessageLike messageLike) {
        messageLikeDao.remove(messageLike);
    }
}
