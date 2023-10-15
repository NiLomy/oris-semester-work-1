package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface PostDao<T> {
    T get(int id);
    T get(String name, int authorId);
    List<T> getAll();
    void save(T t);
    void updateLikes(String name, int likes);
}
