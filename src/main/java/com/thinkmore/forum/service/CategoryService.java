package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.mapper.CategoryMapper;
import com.thinkmore.forum.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;

    private final CategoryMapper categoryMapper;

    public List<CategoryGetDto> getAllCategories() {

        return categoryRepo.findAll().stream()
                .map(category -> categoryMapper.fromEntity(category))
                .collect(Collectors.toList());
    }
}
