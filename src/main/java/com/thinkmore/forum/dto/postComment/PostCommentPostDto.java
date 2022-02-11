package com.thinkmore.forum.dto.postComment;

import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostCommentPostDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto postUsers;
    private PostMiniGetDto post;
    private PostCommentMiniGetDto parentComment;
    private String context;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
