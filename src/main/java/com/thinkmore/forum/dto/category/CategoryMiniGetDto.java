package com.thinkmore.forum.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryMiniGetDto {
    private UUID id;
    private String title;
}
