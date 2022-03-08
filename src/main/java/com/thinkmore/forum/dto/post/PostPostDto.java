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
public class PostPostDto implements Serializable {
    private String categoryTitle;
    private String headImgUrl;
    private String title;
    private String context;
    private OffsetDateTime createTimestamp;
}
