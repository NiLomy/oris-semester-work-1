package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    T get(String login, String password);
    T get(String login, String password, String email);
    List<T> getAll();
    void save(T t);
}
