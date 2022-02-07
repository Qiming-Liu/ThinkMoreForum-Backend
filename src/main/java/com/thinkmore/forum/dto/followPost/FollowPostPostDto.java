package com.thinkmore.forum.dto.followPost;

import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.dto.users.UsersPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowPostPostDto implements Serializable {
    private UUID id;
    private UsersPostDto users;
    private PostPostDto post;
    private OffsetDateTime createTimestamp;
}
