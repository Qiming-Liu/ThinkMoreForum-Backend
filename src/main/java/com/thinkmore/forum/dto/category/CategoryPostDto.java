package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.img.ImgPostDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryPostDto implements Serializable {
    private final UUID id;
    private final PostPostDto pinPost;
    private final ImgPostDto profileImg;
    private final String color;
    private final String title;
    private final String description;
    private final Integer postCount;
    private final Integer sortOrder;
}
