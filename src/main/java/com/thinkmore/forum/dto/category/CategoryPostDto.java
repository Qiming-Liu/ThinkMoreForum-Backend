package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.post.PostPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryPostDto implements Serializable {
    private UUID id;
    private PostPostDto pinPost;
    private String headImgUrl;
    private Integer type;
    private String color;
    private String title;
    private String description;
    private Integer postCount;
    private Integer sortOrder;
}
