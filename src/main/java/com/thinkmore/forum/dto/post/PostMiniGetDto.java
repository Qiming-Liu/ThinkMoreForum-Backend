package com.thinkmore.forum.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PostMiniGetDto {
    private UUID id;
    private String title;
}
