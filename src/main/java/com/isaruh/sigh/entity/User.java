package com.isaruh.sigh.entity;

import com.isaruh.sigh.model.response.UserResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // define entity
@Table(name = "users") // set table name
@Getter @Setter // generate getter and setter
@NoArgsConstructor @AllArgsConstructor // set constructor
@Builder // generate builder
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column() // nullable
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(
            name = "liked_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @Builder.Default
    private Set<Post> likes = new HashSet<>();

    public void addLikedPost(Post post) {
        if (likes.contains(post)) return;

        likes.add(post);
        post.getLikes().add(this);
    }

    public void removeLikedPost(Post post) {
        likes.remove(post);
        post.getLikes().remove(this);
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(getId())
                .name(getName())
                .username(getUsername())
                .email(getEmail())
                .build();
    }
}
