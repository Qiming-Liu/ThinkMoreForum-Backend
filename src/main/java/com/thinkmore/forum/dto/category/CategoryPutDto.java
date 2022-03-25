package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.post.PostPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryPutDto implements Serializable {
    private UUID id;
    private PostPutDto pinPost;
    private String headImgUrl;
    private Integer type;
    private String color;
    private String title;
    private String description;
    private Integer viewCount;
    private Integer postCount;
    private Integer sortOrder;
    private Integer participantCount;
    private OffsetDateTime lastUpdateTimestamp;
}
