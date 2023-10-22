package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface MessageDao<T> {
    T get(int id);
    List<T> getAllFromPost(String post);
    List<T> getAll();
    void save(T t);
    void updateLikes(int id, int likes);
    int getMostFrequentUserId();
}
