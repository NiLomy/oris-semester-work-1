package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto get(int id);
    PostDto get(String name, String author);
    List<PostDto> getAll();
    List<PostDto> getAllFromUser(String nickname);
    List<PostDto> getAllFavouriteFromUser(String nickname);
    void save(Post post);
    void saveToFavourites(String nickname, String postName, String authorName);
    void removeFromFavourites(String nickname, String postName, String authorName);
    void updateLikes(String name, int likes);
}
