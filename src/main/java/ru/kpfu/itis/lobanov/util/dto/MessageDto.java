package ru.kpfu.itis.lobanov.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class MessageDto {
    private int id;
    private String author;
    private String content;
    private String post;
    private Timestamp date;
    private int likes;
    private String authorImgUrl;
}
