package ru.kpfu.itis.lobanov.model.dao;

import java.util.List;

public interface PostDao<T> {
    T get(int id);
    T get(String name);
    T get(String name, int authorId);
    List<T> getAll();
    List<T> getAllFromUser(int authorId);
    List<T> getAllFavouritesFromUser(int user_id);
    void save(T t);
    void saveToFavourites(int user_id, int post_id);
    void removeFromFavourites(int user_id, int post_id);
    void updateLikes(String name, int likes);
}
