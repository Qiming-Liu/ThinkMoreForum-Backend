package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.dto.img.ImgPostDto;
import com.thinkmore.forum.dto.img.ImgPutDto;
import com.thinkmore.forum.entity.Img;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImgMapper {
    Img toEntity(ImgPostDto imgPostDto);

    ImgGetDto fromEntity(Img img);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(ImgPutDto imgPutDto, @MappingTarget Img img);
}
