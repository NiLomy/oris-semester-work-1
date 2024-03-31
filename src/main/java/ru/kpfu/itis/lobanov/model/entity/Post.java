package ru.kpfu.itis.lobanov.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "add_date")
    private Timestamp date;
    private int likes;

    public Post(@NonNull String name, @NonNull String category, @NonNull String content, User author, @NonNull Timestamp date, int likes) {
        this.name = name;
        this.category = category;
        this.content = content;
        this.author = author;
        this.date = date;
        this.likes = likes;
    }
}
