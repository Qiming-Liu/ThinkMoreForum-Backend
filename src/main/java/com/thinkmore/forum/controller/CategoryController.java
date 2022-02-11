package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "/addCategory")
    public CategoryGetDto addCategory(@RequestParam String title, @RequestParam String description, @RequestParam String color) {
        return categoryService.addCategory(title, description, color);
    }

}
