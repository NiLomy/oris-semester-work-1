package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface MessageLikeDao<T> {
    T get(int id);
    T get(String author, int messageId);
    List<T> getAllFromMessage(int messageId);
    List<T> getAll();
    void save(T t);
    void remove(T t);
}
