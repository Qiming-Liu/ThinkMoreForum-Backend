package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import com.thinkmore.forum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PostRepository postRepository;

    public List<CategoryGetDto> getAllCategories() {
        return categoryRepository.findByOrderBySortOrderAsc().stream().map(categoryMapper::fromEntity).collect(Collectors.toList());
    }

    public CategoryGetDto getCategoryByCategoryTitle(String category_title) throws Exception {
        Optional<Category> targetCategory = categoryRepository.findByTitle(category_title);
        CategoryGetDto targetCategoryGetDto;
        if (targetCategory.isPresent()) {
            targetCategoryGetDto = categoryMapper.fromEntity(targetCategory.get());
        } else {
            throw new Exception("Couldn't find the category with provided ID");
        }
        return targetCategoryGetDto;
    }

    public Boolean putCategory(List<CategoryPutDto> categoryPutDtoList) {
        List<Category> categoryNewList = categoryPutDtoList.stream().map(categoryMapper::toEntity).collect(Collectors.toList());
        List<Category> categoryOldList = categoryRepository.findByOrderBySortOrderAsc();
        for (int i = 0; i < categoryNewList.size(); i++) {
            categoryNewList.get(i).setSortOrder(i);
        }

        List<Category> removeList = categoryOldList.stream().filter(category -> {
            for (Category categoryNew : categoryNewList) {
                if (categoryNew.getTitle().equals(category.getTitle())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        List<Category> addList = categoryNewList.stream().filter(category -> {
            for (Category categoryOld : categoryOldList) {
                if (categoryOld.getTitle().equals(category.getTitle())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        removeList.forEach(category ->
                postRepository.findByCategory_Title(category.getTitle())
                        .forEach(post -> post.setCategory(null)));

        categoryRepository.deleteAll(removeList);
        categoryRepository.saveAll(addList);
        return true;
    }

    public CategoryGetDto putCategoryPinPostById(UUID categoryId, UUID postId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setPinPost(postRepository.findById(postId).get());
        categoryRepository.save(category);
        return categoryMapper.fromEntity(category);
    }

    public CategoryGetDto putCategoryPinPostNull(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setPinPost(null);
        categoryRepository.save(category);
        return categoryMapper.fromEntity(category);
    }
}
