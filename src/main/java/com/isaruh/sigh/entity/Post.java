package com.isaruh.sigh.entity;

import com.isaruh.sigh.model.response.PostResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String caption;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "likedPosts")
    private Set<User> likes = new HashSet<>();

    public PostResponse toResponse() {
        return PostResponse.builder()
                .id(getId())
                .caption(getCaption())
                .user(getUser())
                .likes(getLikes().size())
                .build();
    }
}
