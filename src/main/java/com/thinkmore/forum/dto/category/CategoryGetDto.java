package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.post.PostMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryGetDto implements Serializable {
    private UUID id;
    private PostMiniGetDto pinPost;
    private String headImgUrl;
    private String color;
    private String title;
    private String description;
    private Integer viewCount;
    private Integer postCount;
    private Integer sortOrder;
    private Integer participantCount;
    private OffsetDateTime lastUpdateTimestamp;
}
