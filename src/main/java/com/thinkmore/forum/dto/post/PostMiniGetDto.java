package com.thinkmore.forum.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PostMiniGetDto implements Serializable {
    private UUID id;
    private String title;
}
