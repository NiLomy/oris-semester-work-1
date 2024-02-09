package ru.kpfu.itis.lobanov.model.dao;

import ru.kpfu.itis.lobanov.model.entity.Post;

import java.util.List;

public interface PostDao extends Dao<Post> {
    Post get(String name, int authorId);

    List<Post> getAllFromUser(int authorId);

    List<Post> getAllFavouritesFromUser(int userId);

    void saveToFavourites(int userId, int postId);

    void removeFromFavourites(int userId, int postId);

    void updateLikes(String name, int likes);
}
