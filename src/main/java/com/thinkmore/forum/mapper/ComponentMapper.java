package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.component.ComponentGetDto;
import com.thinkmore.forum.dto.component.ComponentPostDto;
import com.thinkmore.forum.dto.component.ComponentPutDto;
import com.thinkmore.forum.entity.Component;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ComponentMapper {

    Component toEntity(ComponentPutDto componentPutDto);

    Component toEntity(ComponentPostDto ComponentPostDto);

    ComponentGetDto fromEntity(Component component);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(ComponentPutDto componentPutDto, @MappingTarget Component component);
}
