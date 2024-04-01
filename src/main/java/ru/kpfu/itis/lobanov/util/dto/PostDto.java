package ru.kpfu.itis.lobanov.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostDto {
    private String name;
    private String category;
    private String content;
    private String author;
    private Timestamp date;
    private int likes;
    private String authorImageUrl;
}
