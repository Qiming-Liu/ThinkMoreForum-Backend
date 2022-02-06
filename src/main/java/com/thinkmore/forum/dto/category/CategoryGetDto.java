package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.dto.post.PostGetDto;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryGetDto implements Serializable {
    private final UUID id;
    private final PostGetDto pinPost;
    private final ImgGetDto profileImg;
    private final String color;
    private final String title;
    private final String description;
    private final Integer postCount;
    private final Integer sortOrder;
}
