package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import java.util.List;

public interface PostService {
    PostDto get(int id);

    PostDto get(String name, String author);

    PostDto getMostPopularPost();

    List<PostDto> getAll();

    List<PostDto> getAllFromUser(String nickname);

    List<PostDto> getAllFavouriteFromUser(String nickname);

    void save(String postName, String postCategory, String postText, String userLogin);

    void saveToFavourites(PostDto postDto, UserDto userDto);

    void removeFromFavourites(PostDto postDto, UserDto userDto);

    PostDto updateLikes(PostDto postDto, UserDto userDto);

    boolean isPostUnique(String author, String postName);

    boolean isPostFavourite(List<PostDto> favouritePosts, String postName);

    String checkForValid(String postName, String postCategory, String postText);
}
