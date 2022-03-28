package com.thinkmore.forum.dto.post;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PostCommentGetDto implements Serializable {
    private PostGetDto post;
    private List<CommentGetDto> comments;
}
