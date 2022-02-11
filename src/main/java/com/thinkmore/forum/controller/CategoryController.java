package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
