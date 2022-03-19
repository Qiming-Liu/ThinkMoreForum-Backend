package com.thinkmore.forum.dto.comment;

import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommentPostDto implements Serializable {
    private UsersMiniGetDto commentUsers;
    private UsersMiniGetDto mentionUsers;
    private PostMiniGetDto post;
    private CommentMiniGetDto parentComment;
    private String context;
    private Boolean visibility;
}
