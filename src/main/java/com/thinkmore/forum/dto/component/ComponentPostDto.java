package com.thinkmore.forum.dto.component;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ComponentPostDto implements Serializable {
    private UUID id;
    private String name;
    private String code;
}
