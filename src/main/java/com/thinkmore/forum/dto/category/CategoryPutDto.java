package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.img.ImgPutDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryPutDto implements Serializable {
    private final UUID id;
    private final PostPutDto pinPost;
    private final ImgPutDto profileImg;
    private final String color;
    private final String title;
    private final String description;
    private final Integer postCount;
    private final Integer sortOrder;
}
