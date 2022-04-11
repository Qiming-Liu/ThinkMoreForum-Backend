package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.service.CategoryService;
import com.thinkmore.forum.util.Util;

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

    @PutMapping
    public ResponseEntity<Boolean> putCategories(@RequestBody List<CategoryPutDto> categoryPutDtoList) {
        Util.checkPermission("adminManagement");
        return ResponseEntity.ok(categoryService.putCategory(categoryPutDtoList));
    }

    @PutMapping(path = "{category_id}/pin/{post_id}")
    public ResponseEntity<CategoryGetDto> putCategoryPinPostById(@PathVariable String category_id, @PathVariable String post_id) {
        Util.checkPermission("postManagement");
        UUID categoryId = UUID.fromString(category_id);
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(categoryService.putCategoryPinPostById(categoryId, postId));
    }

    @PutMapping(path = "{category_id}/unpin")
    public ResponseEntity<CategoryGetDto> putCategoryPinPostNull(@PathVariable String category_id) {
        Util.checkPermission("postManagement");
        UUID categoryId = UUID.fromString(category_id);
        return ResponseEntity.ok(categoryService.putCategoryPinPostNull(categoryId));
    }
}
