package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface PostLikeDao<T> {
    T get(int id);
    T get(String nickname, String post);
    List<T> getAllFromPost(String post);
    List<T> getAll();
    void save(T t);
    void remove(T t);
}
