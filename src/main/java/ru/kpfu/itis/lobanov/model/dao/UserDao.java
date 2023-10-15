package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface UserDao<T> {
    T get(int id);
    T get(String nickname);
    T get(String login, String password);
    List<T> getAll();
    String getEmail(String login, String password);
    void save(T t);
    void update(T t, String oldLogin);
    void updateImageUrl(String nickName, String imageUrl);
    void updatePassword(String nickName, String password);
}
