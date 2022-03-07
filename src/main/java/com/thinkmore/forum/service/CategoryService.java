package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepo.findByOrderBySortOrderAsc().stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public CategoryGetDto getCategoryByCategoryTitle(String category_title) throws Exception {
        Optional<Category> targetCategory = categoryRepo.findByTitle(category_title);
        CategoryGetDto targetCategoryGetDto;
        if (targetCategory.isPresent()) {
            targetCategoryGetDto = categoryMapper.fromEntity(targetCategory.get());
        } else {
            throw new Exception("Couldn't find the category with provided ID");
        }
        return targetCategoryGetDto;
    }
}
