package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "/addCategory/{title}/{description}/{color}")
    public CategoryGetDto addCategory(@PathVariable("title") String title, @PathVariable("description") String description, @PathVariable("color") String color) {
        return categoryService.addCategory(title, description, color);
    }

    @PutMapping
    public ResponseEntity<Boolean> changeCategory(@RequestBody CategoryPutDto categoryPutDto) {
        return ResponseEntity.ok(categoryService.changedCategory(categoryPutDto));
    }

    @DeleteMapping
    public void deleteCategory(@RequestParam String category_id){
        UUID uuid = UUID.fromString(category_id);
        categoryService.deleteCategory(uuid);
    }

}
