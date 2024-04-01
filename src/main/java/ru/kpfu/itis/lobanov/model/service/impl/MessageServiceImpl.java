package ru.kpfu.itis.lobanov.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.model.entity.User;
import ru.kpfu.itis.lobanov.model.repositories.MessageLikeRepository;
import ru.kpfu.itis.lobanov.model.repositories.MessageRepository;
import ru.kpfu.itis.lobanov.model.repositories.UserRepository;
import ru.kpfu.itis.lobanov.model.service.MessageLikeService;
import ru.kpfu.itis.lobanov.model.service.MessageService;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
import ru.kpfu.itis.lobanov.util.dto.PostDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;
import ru.kpfu.itis.lobanov.util.exception.MessageNotFoundException;
import ru.kpfu.itis.lobanov.util.exception.UserNotFoundException;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    public final UserRepository userRepository;
    public final MessageRepository messageRepository;
    private final MessageLikeRepository messageLikeRepository;
    private final MessageLikeService messageLikeService;

    @Override
    public MessageDto get(int id) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        if (message == null) return null;
        User user = userRepository.findById(message.getAuthorId()).orElseThrow(UserNotFoundException::new);
        return new MessageDto(
                message.getId(),
                user.getLogin(),
                message.getContent(),
                message.getPost(),
                message.getDate(),
                message.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public MessageDto get(String author, String content, String post, Timestamp date, int likes) {
        User user = userRepository.findByLogin(author);
        Message message = messageRepository.findMessagesByAuthorIdAndContentAndPostAndDateAndLikes(user.getId(), content, post, date, likes);
        if (message == null) return null;
        return new MessageDto(
                message.getId(),
                user.getLogin(),
                message.getContent(),
                message.getPost(),
                message.getDate(),
                message.getLikes(),
                user.getImageUrl()
        );
    }

    @Override
    public List<MessageDto> getAllFromPost(String post) {
        return messageRepository.findAllByPost(post).stream().map(
                message -> {
                    User user = userRepository.findById(message.getAuthorId()).orElseThrow(UserNotFoundException::new);
                    return new MessageDto(
                            message.getId(),
                            user.getLogin(),
                            message.getContent(),
                            message.getPost(),
                            message.getDate(),
                            message.getLikes(),
                            user.getImageUrl()
                    );
                }
        ).sorted(Comparator.comparing(MessageDto::getDate)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getAll() {
        return messageRepository.findAll().stream().map(
                message -> {
                    User user = userRepository.findById(message.getAuthorId()).orElseThrow(UserNotFoundException::new);
                    return new MessageDto(
                            message.getId(),
                            user.getLogin(),
                            message.getContent(),
                            message.getPost(),
                            message.getDate(),
                            message.getLikes(),
                            user.getImageUrl()
                    );
                }
        ).collect(Collectors.toList());
    }

    @Override
    public MessageDto save(String newMessage, PostDto postDto, UserDto userDto) {
        Timestamp date = getDate();
        User user = userRepository.findByLogin(userDto.getLogin());

        messageRepository.save(new Message(
                user.getId(), newMessage, postDto.getName(), date, 0
        ));
        return get(user.getLogin(), newMessage, postDto.getName(), date, 0);
    }

    @Override
    public int updateLikes(int messageId, UserDto userDto) {
        MessageDto message = get(messageId);
        int likes = message.getLikes();

        if (messageLikeService.isSet(userDto.getLogin(), messageId)) {
            messageLikeService.remove(messageLikeRepository.findByAuthorAndMessageId(userDto.getLogin(), messageId));
            likes--;
        } else {
            messageLikeService.save(new MessageLike(userDto.getLogin(), messageId));
            likes++;
        }
        messageRepository.updateLikes(messageId, likes);
        message.setLikes(likes);

        return likes;
    }

    @Override
    public UserDto getMostFrequentUser() {
        User user = userRepository.findById(messageRepository.getMostFrequentUserId().get(0)).orElseThrow(UserNotFoundException::new);
        if (user == null) return null;
        return new UserDto(
                user.getName(),
                user.getLastname(),
                user.getLogin(),
                user.getEmail(),
                user.getImageUrl(),
                user.getAboutMe()
        );
    }

    private Timestamp getDate() {
        String[] dateInput = ZonedDateTime.now().toString().split(ServerResources.TIME_CHAR);
        String[] timeInput = dateInput[1].split(ServerResources.DOT_CHAT);
        String stringDate = dateInput[0];
        String stringTime = timeInput[0];

        return Timestamp.valueOf(stringDate + " " + stringTime);
    }
}
