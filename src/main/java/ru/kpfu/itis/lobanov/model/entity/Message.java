package ru.kpfu.itis.lobanov.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "author_id")
    private int authorId;
    private String content;
    private String post;
    private Timestamp date;
    private int likes;

    public Message(int authorId, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes) {
        this.authorId = authorId;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }

    public Message(int id, int authorId, @NonNull String content, @NonNull String post, @NonNull Timestamp date, int likes) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.post = post;
        this.date = date;
        this.likes = likes;
    }
}
