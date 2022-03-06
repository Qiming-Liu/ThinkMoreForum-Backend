package com.thinkmore.forum.dto.followPost;

import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowPostPostDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto users;
    private PostMiniGetDto post;
    private OffsetDateTime createTimestamp;
}
