package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.exception.CategoryNotFoundException;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;

    private final CategoryMapper categoryMapper;

    public CategoryGetDto addCategory (String title, String description, String color) {
        Category category = new Category();

        category.setTitle(title);
        category.setDescription(description);
        category.setColor(color);
        category.setProfileImg(null);
        category.setPostCount(null);
        category.setSortOrder(null);
        category.setPinPost(null);

        categoryRepo.save(category);


        return categoryMapper.fromEntity(category);
    }


    public CategoryGetDto getCategoryByTitle(String title) {

        return categoryMapper.fromEntity(CategoryRepository.findByTitle(title).orElseThrow(() ->
                new CategoryNotFoundException(String.format("Category %s not found", title))));
    }



    public List<CategoryGetDto> getAllCategories() {

        return categoryRepo.findAll().stream()
                .map(category -> categoryMapper.fromEntity(category))
                .collect(Collectors.toList());
    }
}
