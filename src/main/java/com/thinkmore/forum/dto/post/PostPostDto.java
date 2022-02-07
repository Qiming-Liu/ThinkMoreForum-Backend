package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.category.CategoryPostDto;
import com.thinkmore.forum.dto.users.UsersPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostPostDto implements Serializable {
    private UUID id;
    private UsersPostDto postUsers;
    private CategoryPostDto category;
    private String title;
    private String context;
    private Integer viewCount;
    private Integer followCount;
    private Integer commentCount;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
