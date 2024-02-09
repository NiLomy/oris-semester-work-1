package ru.kpfu.itis.lobanov.model.service.impl;

import ru.kpfu.itis.lobanov.model.dao.PostDao;
import ru.kpfu.itis.lobanov.model.dao.UserDao;
import ru.kpfu.itis.lobanov.model.dao.impl.PostDaoImpl;
import ru.kpfu.itis.lobanov.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.service.PostLikeService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final UserDao userDao;
    private final PostLikeService postLikeService;

    public PostServiceImpl(PostDao postDao, UserDao userDao, PostLikeService postLikeService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.postLikeService = postLikeService;
    }

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
    public PostDto getMostPopularPost() {
        PostDto postDto = null;
        List<PostDto> posts = getAll();
        if (!posts.isEmpty()) {
            postDto = posts.stream().max(Comparator.comparingInt(PostDto::getLikes)).get();
        }
        return postDto;
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
        ).sorted((o1, o2) -> {
            int temp = o2.getLikes() - o1.getLikes();
            if (temp != 0) {
                return temp;
            } else return o2.getDate().compareTo(o1.getDate());
        }).collect(Collectors.toList());
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
        ).sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).collect(Collectors.toList());
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
        ).sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate())).collect(Collectors.toList());
    }

    @Override
    public void save(String postName, String postCategory, String postText, String userLogin) {
        User user = userDao.get(userLogin);
        Timestamp date = getDate();

        postDao.save(
                new Post(
                        postName,
                        postCategory,
                        postText,
                        user.getId(),
                        date,
                        0
                )
        );
    }

    @Override
    public void saveToFavourites(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");

        User currentUser = userDao.get(userDto.getLogin());
        User author = userDao.get(postDto.getAuthor());
        Post currentPost = postDao.get(postDto.getName(), author.getId());
        postDao.saveToFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public void removeFromFavourites(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");

        User currentUser = userDao.get(userDto.getLogin());
        User author = userDao.get(postDto.getAuthor());
        Post currentPost = postDao.get(postDto.getName(), author.getId());
        postDao.removeFromFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public void updateLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        PostDto postDto = (PostDto) httpSession.getAttribute("currentPost");
        UserDto userDto = (UserDto) httpSession.getAttribute("currentUser");
        int likes = postDto.getLikes();

        // вынести в сервлет и сделать отдальным методом у сервиса
        if (postLikeService.isSet(userDto.getLogin(), postDto.getName())) {
            postLikeService.remove(new PostLike(userDto.getLogin(), postDto.getName()));
            likes--;
        } else {
            postLikeService.save(new PostLike(userDto.getLogin(), postDto.getName()));
            likes++;
        }
        postDao.updateLikes(postDto.getName(), likes);
        postDto.setLikes(likes);
        httpSession.setAttribute("currentPost", postDto);

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(likes));
    }

    @Override
    public boolean isPostUnique(String author, String postName) {
        PostDto post = get(postName, author);
        return post == null;
    }

    @Override
    public boolean isPostValid(String postName, String postCategory, String postText, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        if (postName.isEmpty()) {
            resp.getWriter().write("emptyPostName");
            return false;
        }
        if (postName.length() < 5) {
            resp.getWriter().write("shortPostName");
            return false;
        }
        if (postName.length() > 100) {
            resp.getWriter().write("longPostName");
            return false;
        }
        if (postCategory.isEmpty()) {
            resp.getWriter().write("emptyPostCategory");
            return false;
        }
        if (postText.isEmpty()) {
            resp.getWriter().write("emptyPostText");
            return false;
        }

        return true;
    }

    private Timestamp getDate() {
        String[] dateInput = ZonedDateTime.now().toString().split("T");
        String[] timeInput = dateInput[1].split("\\.");
        String stringDate = dateInput[0];
        String stringTime = timeInput[0];

        return Timestamp.valueOf(stringDate + " " + stringTime);
    }
}
