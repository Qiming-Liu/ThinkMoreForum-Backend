package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.dto.img.ImgPutDto;
import com.thinkmore.forum.dto.users.UsersPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostPutDto implements Serializable {
    private UUID id;
    private UsersPutDto postUsers;
    private CategoryPutDto category;
    private ImgPutDto headImg;
    private String title;
    private String context;
    private Integer viewCount;
    private Integer followCount;
    private Integer commentCount;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
