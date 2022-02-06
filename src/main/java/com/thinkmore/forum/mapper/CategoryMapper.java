package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPostDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.entity.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryPutDto categoryPutDto);

    CategoryGetDto fromEntity(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(CategoryPostDto categoryPostDto, @MappingTarget Category category);
}
