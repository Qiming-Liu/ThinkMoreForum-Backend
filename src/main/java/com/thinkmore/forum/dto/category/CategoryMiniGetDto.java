package com.thinkmore.forum.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryMiniGetDto implements Serializable {
    private UUID id;
    private String title;
}
