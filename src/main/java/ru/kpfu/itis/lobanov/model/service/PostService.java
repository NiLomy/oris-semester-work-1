package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.util.dto.PostDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDto get(int id);
    PostDto get(String name, String author);
    PostDto getMostPopularPost();
    List<PostDto> getAll();
    List<PostDto> getAllFromUser(String nickname);
    List<PostDto> getAllFavouriteFromUser(String nickname);
    void save(String postName, String postCategory, String postText, String userLogin);
    void saveToFavourites(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void removeFromFavourites(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void updateLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    boolean isPostUnique(String author, String postName);
    boolean isPostValid(String postName, String postCategory, String postText, HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
