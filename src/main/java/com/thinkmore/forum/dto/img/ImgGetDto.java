package com.thinkmore.forum.dto.img;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ImgGetDto implements Serializable {
    private UUID id;
    private String url;
    private String md5;
}
