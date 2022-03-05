package com.thinkmore.forum.dto.Comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CommentMiniGetDto implements Serializable {
    private UUID id;
    private String context;
}
