package ru.kpfu.itis.lobanov.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "likes_for_posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;
    private String post;

    public PostLike(@NonNull String nickname, @NonNull String post) {
        this.nickname = nickname;
        this.post = post;
    }
}
