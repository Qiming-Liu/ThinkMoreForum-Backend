package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.category.CategoryMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostGetDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto postUsers;
    private CategoryMiniGetDto category;
    private String headImgUrl;
    private String title;
    private String context;
    private Integer viewCount;
    private Integer followCount;
    private Integer commentCount;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
