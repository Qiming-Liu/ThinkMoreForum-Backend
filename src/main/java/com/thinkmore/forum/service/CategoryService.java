package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryGetDto addCategory (String title, String description, String color) {
        Category category = new Category();

        int nowSortOrder = getAllCategories().size();

        category.setTitle(title);
        category.setDescription(description);
        category.setColor(color);
        category.setPostCount(0);
        category.setSortOrder(nowSortOrder);
        category.setPinPost(null);

        categoryRepo.save(category);
        return categoryMapper.fromEntity(category);
    }

    @Transactional
    public CategoryGetDto getCategoryById(UUID id) {
        return categoryMapper.fromEntity(categoryRepo.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(String.format("Category %s not found", id))));
    }

    @Transactional
    public void deleteCategory(UUID uuid) {
        AtomicInteger count = new AtomicInteger();
        categoryRepo.deleteById(uuid);
        categoryRepo.findByOrderBySortOrderAsc().stream()
                .forEach((category) -> {
                    category.setSortOrder(count.getAndIncrement());
                    categoryRepo.save(category);
                });
        System.out.println("success delete");
    }

    @Transactional
    public boolean changedCategory(CategoryPutDto categoryPutDto) {
        Category oldCategory = categoryRepo.findById(categoryPutDto.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Invalid Title"));

        oldCategory.setDescription(String.valueOf(categoryPutDto));
        categoryMapper.copy(categoryPutDto, oldCategory);
        return true;
    }

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepo.findByOrderBySortOrderAsc().stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoryMiniGetDto> getAllCategoriesCoreInfo() {
        return categoryRepo.findAll().stream()
                .map(categoryMapper::entityToMiniDto)
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
