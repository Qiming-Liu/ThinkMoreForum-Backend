package com.thinkmore.forum.dto.category;

import com.thinkmore.forum.dto.img.ImgPutDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryPutDto implements Serializable {
    private UUID id;
    private PostPutDto pinPost;
    private ImgPutDto headImg;
    private Integer type;
    private String color;
    private String title;
    private String description;
    private Integer postCount;
    private Integer sortOrder;
}
