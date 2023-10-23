package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.User;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t, int id);
    void remove(T t);
}
