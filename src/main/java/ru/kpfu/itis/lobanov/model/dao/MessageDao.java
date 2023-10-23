package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.Message;

import java.sql.Timestamp;
import java.util.List;

public interface MessageDao extends Dao<Message> {
    Message get(int authorId, String content, String post, Timestamp date, int likes);
    List<Message> getAllFromPost(String post);
    void updateLikes(int id, int likes);
    int getMostFrequentUserId();
}
