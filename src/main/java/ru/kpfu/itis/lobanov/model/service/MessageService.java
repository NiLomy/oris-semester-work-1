package ru.kpfu.itis.lobanov.model.service;

import ru.kpfu.itis.lobanov.model.entity.Message;
import ru.kpfu.itis.lobanov.util.dto.MessageDto;
import ru.kpfu.itis.lobanov.util.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public interface MessageService {
    MessageDto get(int id);
    MessageDto get(String author, String content, String post, Timestamp date, int likes);
    List<MessageDto> getAllFromPost(String post);
    List<MessageDto> getAll();
    void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void updateLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    UserDto getMostFrequentUser();
}
