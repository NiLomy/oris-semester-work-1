package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.util.dto.MessageLikeDto;

public interface MessageLikeService {
    MessageLikeDto get(int id);

    boolean isSet(String author, int messageId);

    void save(MessageLike messageLike);

    void remove(MessageLike messageLike);
}
