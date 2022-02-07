package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.users.UsersGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostGetDto implements Serializable {
    private UUID id;
    private UsersGetDto postUsers;
    private CategoryGetDto category;
    private String title;
    private String context;
    private Integer viewCount;
    private Integer followCount;
    private Integer commentCount;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
