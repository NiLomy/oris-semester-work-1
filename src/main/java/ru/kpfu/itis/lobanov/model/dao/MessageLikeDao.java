package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.MessageLike;

import java.util.List;

public interface MessageLikeDao extends Dao<MessageLike> {
    MessageLike get(String author, int messageId);

    List<MessageLike> getAllFromMessage(int messageId);
}
