package ru.kpfu.itis.lobanov.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.model.entity.MessageLike;
import ru.kpfu.itis.lobanov.model.repositories.MessageLikeRepository;
import ru.kpfu.itis.lobanov.model.service.MessageLikeService;
import ru.kpfu.itis.lobanov.util.dto.MessageLikeDto;
import ru.kpfu.itis.lobanov.util.exception.MessageLikeNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageLikeServiceImpl implements MessageLikeService {
    private final MessageLikeRepository messageLikeRepository;

    @Override
    public MessageLikeDto get(int id) {
        MessageLike messageLike = messageLikeRepository.findById(id).orElseThrow(MessageLikeNotFoundException::new);
        return new MessageLikeDto(messageLike.getAuthor(), messageLike.getMessageId());
    }

    @Override
    public boolean isSet(String author, int messageId) {
        MessageLike messageLike = messageLikeRepository.findByAuthorAndMessageId(author, messageId);
        return messageLike != null;
    }

    @Override
    public int getAmountFromPost(int messageId) {
        return messageLikeRepository.findAllByMessageId(messageId).size();
    }

    @Override
    public int getAmount() {
        return messageLikeRepository.findAll().size();
    }

    @Override
    public void save(MessageLike messageLike) {
        messageLikeRepository.save(messageLike);
    }

    @Override
    public void remove(MessageLike messageLike) {
        messageLikeRepository.delete(messageLike);
    }
}
