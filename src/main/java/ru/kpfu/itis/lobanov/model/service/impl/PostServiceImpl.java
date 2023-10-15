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
    private final PostDao<Post> postDao = new PostDaoImpl();
    private final UserDao<User> userDao = new UserDaoImpl();

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
                post.getLikes()
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
                post.getLikes()
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
                        post.getLikes()
                    );
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void save(Post post) {
        postDao.save(post);
    }

    @Override
    public void updateLikes(String name, int likes) {
        postDao.updateLikes(name, likes);
    }
}
