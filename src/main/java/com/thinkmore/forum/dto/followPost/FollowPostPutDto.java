package com.thinkmore.forum.dto.followPost;

import com.thinkmore.forum.dto.post.PostPutDto;
import com.thinkmore.forum.dto.users.UsersPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowPostPutDto implements Serializable {
    private UUID id;
    private UsersPutDto users;
    private PostPutDto post;
    private OffsetDateTime createTimestamp;
}
