package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryMiniGetDto;
import com.thinkmore.forum.dto.category.CategoryPostDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.entity.Category;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryPostDto categoryPostDto);

    CategoryGetDto fromEntity(Category category);

    CategoryMiniGetDto entityToMiniDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(CategoryPutDto categoryPutDto, @MappingTarget Category category);
}
