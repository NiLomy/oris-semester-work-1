package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    T get(String login, String password);
    List<T> getAll();
    String getEmail(String login, String password);
    void save(T t);
    void update(T t, String oldLogin);
}
