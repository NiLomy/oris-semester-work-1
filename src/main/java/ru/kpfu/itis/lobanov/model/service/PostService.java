package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.dto.PostDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PostService {
    PostDto get(int id);
    PostDto get(String name, String author);
    List<PostDto> getAll();
    List<PostDto> getAllFavouriteFromUser(String nickname);
    void save(Post post);
    void saveToFavourites(String nickname, String postName);
    void removeFromFavourites(String nickname, String postName);
    void updateLikes(String name, int likes);
}
