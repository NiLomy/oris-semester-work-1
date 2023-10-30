package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.PostDao;
import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.PostDaoImpl;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.dto.PostDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {
    private final PostDao postDao = new PostDaoImpl();
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public PostDto get(int id) {
        Post post = postDao.get(id);
        if (post == null) return null;
        User user = userDao.get(post.getAuthorId());
        return new PostDto(
                post.getName(),
                post.getCategory(),
                post.getContent(),
                user.getLogin(),
                post.getDate(),
                post.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public PostDto get(String name, String author) {
        User user = userDao.get(author);
        Post post = postDao.get(name, user.getId());
        if (post == null) return null;
        return new PostDto(
                post.getName(),
                post.getCategory(),
                post.getContent(),
                user.getLogin(),
                post.getDate(),
                post.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public List<PostDto> getAll() {
        return postDao.getAll().stream().map(
                post -> {
                    User user = userDao.get(post.getAuthorId());
                    return new PostDto(
                        post.getName(),
                        post.getCategory(),
                        post.getContent(),
                        user.getLogin(),
                        post.getDate(),
                        post.getLikes(),
                        user.getImageUrl()
                    );
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllFromUser(String nickname) {
        User currentUser = userDao.get(nickname);
        return postDao.getAllFromUser(currentUser.getId()).stream().map(
                post -> new PostDto(
                        post.getName(),
                        post.getCategory(),
                        post.getContent(),
                        nickname,
                        post.getDate(),
                        post.getLikes(),
                        currentUser.getImageUrl()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllFavouriteFromUser(String nickname) {
        User currentUser = userDao.get(nickname);
        return postDao.getAllFavouritesFromUser(currentUser.getId()).stream().map(
                post -> {
                    User user = userDao.get(post.getAuthorId());
                    return new PostDto(
                            post.getName(),
                            post.getCategory(),
                            post.getContent(),
                            user.getLogin(),
                            post.getDate(),
                            post.getLikes(),
                            user.getImageUrl());
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void save(Post post) {
        postDao.save(post);
    }

    @Override
    public void saveToFavourites(String nickname, String postName, String authorName) {
        User currentUser = userDao.get(nickname);
        User author = userDao.get(authorName);
        Post currentPost = postDao.get(postName, author.getId());
        postDao.saveToFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public void removeFromFavourites(String nickname, String postName, String authorName) {
        User currentUser = userDao.get(nickname);
        User author = userDao.get(authorName);
        Post currentPost = postDao.get(postName, author.getId());
        postDao.removeFromFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public void updateLikes(String name, int likes) {
        postDao.updateLikes(name, likes);
    }

    @Override
    public boolean isPostUnique(String author, String postName) {
        PostDto post = get(postName, author);
        return post == null;
    }
}
