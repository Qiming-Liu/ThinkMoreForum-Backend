package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.entity.Img;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImgMapper {
    ImgGetDto fromEntity(Img img);
}
