package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.dto.users.UsersPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostMiniPutDto implements Serializable {
    private String headImgUrl;
    private String title;
    private String context;
}
