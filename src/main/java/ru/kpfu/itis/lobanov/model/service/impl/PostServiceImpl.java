package ru.kpfu.itis.lobanov.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.Post;
import ru.kpfu.itis.lobanov.model.entity.PostLike;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.repositories.PostRepository;
import ru.kpfu.itis.lobanov.model.repositories.UserRepository;
import ru.kpfu.itis.lobanov.model.service.PostLikeService;
import ru.kpfu.itis.lobanov.model.service.PostService;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.PostNotFoundException;
import ru.kpfu.itis.lobanov.util.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeService postLikeService;

    @Override
    public PostDto get(int id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if (post == null) return null;
        User user = userRepository.findById(post.getAuthor().getId()).orElseThrow(UserNotFoundException::new);
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
        User user = userRepository.findByLogin(author);
        Post post = postRepository.findByNameAndAuthor(name, user);
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
        return postRepository.findAll().stream().map(
                post -> {
                    User user = userRepository.findById(post.getAuthor().getId()).orElseThrow(UserNotFoundException::new);
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
        User currentUser = userRepository.findByLogin(nickname);
        return postRepository.findAllByAuthor(currentUser.getId()).stream().map(
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
        User currentUser = userRepository.findByLogin(nickname);
        return postRepository.findAllFavouritesByUser(currentUser.getId()).stream()
                .map(postRepository::findById)
                .map(
                        optionalPost -> {
                            Post post = optionalPost.orElseThrow(PostNotFoundException::new);
                            User user = userRepository.findById(post.getAuthor().getId()).orElseThrow(UserNotFoundException::new);
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
        User user = userRepository.findByLogin(userLogin);
        Timestamp date = getDate();

        postRepository.save(
                new Post(
                        postName,
                        postCategory,
                        postText,
                        user,
                        date,
                        0
                )
        );
    }

    @Override
    public void saveToFavourites(PostDto postDto, UserDto userDto) {
        User currentUser = userRepository.findByLogin(userDto.getLogin());
        User author = userRepository.findByLogin(postDto.getAuthor());
        Post currentPost = postRepository.findByNameAndAuthor(postDto.getName(), author);
        postRepository.saveToFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public void removeFromFavourites(PostDto postDto, UserDto userDto) {
        User currentUser = userRepository.findByLogin(userDto.getLogin());
        User author = userRepository.findByLogin(postDto.getAuthor());
        Post currentPost = postRepository.findByNameAndAuthor(postDto.getName(), author);
        postRepository.removeFromFavourites(currentUser.getId(), currentPost.getId());
    }

    @Override
    public PostDto updateLikes(PostDto postDto, UserDto userDto) {
        int likes = postDto.getLikes();

        if (postLikeService.isSet(userDto.getLogin(), postDto.getName())) {
            postLikeService.remove(new PostLike(userDto.getLogin(), postDto.getName()));
            likes--;
        } else {
            postLikeService.save(new PostLike(userDto.getLogin(), postDto.getName()));
            likes++;
        }
        postRepository.updateLikes(postDto.getName(), likes);
        postDto.setLikes(likes);
        return postDto;
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
