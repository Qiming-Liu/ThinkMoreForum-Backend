package com.thinkmore.forum.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PostPostDto implements Serializable {
    private String categoryTitle;
    private String headImgUrl;
    private String title;
    private String context;
    private OffsetDateTime createTimestamp;
}
