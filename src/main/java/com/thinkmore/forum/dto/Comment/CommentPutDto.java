package com.thinkmore.forum.dto.Comment;

import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CommentPutDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto commentUsers;
    private PostMiniGetDto post;
    private CommentMiniGetDto parentComment;
    private String context;
    private Boolean visibility;
    private OffsetDateTime createTimestamp;
}
