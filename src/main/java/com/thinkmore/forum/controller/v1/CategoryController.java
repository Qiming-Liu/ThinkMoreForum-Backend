package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PutMapping
    public ResponseEntity<Boolean> putCategory(@RequestBody List<CategoryPutDto> categoryPutDtoList) {
        return ResponseEntity.ok(categoryService.putCategory(categoryPutDtoList));
    }
}
