package com.thinkmore.forum.dto.img;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ImgPutDto implements Serializable {
    private UUID id;
    private String imgName;
    private String imgHash;
}