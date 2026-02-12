package com.isaruh.sigh.model.response;

import com.isaruh.sigh.entity.User;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String caption;
    private User user;
    private Boolean isLiked;
    private Integer likes;
}
