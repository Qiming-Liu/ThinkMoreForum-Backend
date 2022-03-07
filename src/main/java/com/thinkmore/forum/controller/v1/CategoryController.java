package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> findAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping(path = "/{category_title}")
    public ResponseEntity<CategoryGetDto> findCategoryByCategoryTitle(@PathVariable("category_title") String category_title) throws Exception {

        return ResponseEntity.ok(categoryService.getCategoryByCategoryTitle(category_title));
    }

    @GetMapping(path = "/{category_title}/count")
    public ResponseEntity<Long> findNumOfPostsInCategory(@PathVariable("category_title") String category_title) {
        return ResponseEntity.ok(postService.getCountOfPostsByCategoryTitle(category_title));
    }
    @GetMapping(path = "/{category_title}/visible-count")
    public ResponseEntity<Long> findNumOfVisiblePostsInCategory(@PathVariable("category_title") String category_title) {
        return ResponseEntity.ok(postService.getCountOfVisiblePostsByCategoryTitle(category_title));
    }

    @GetMapping(path = "/{category_title}/post")
    public ResponseEntity<List<PostGetDto>> findPostsByCategoryTitle(@PathVariable("category_title") String category_title, @PageableDefault(page = 0, size = 10, sort = {"createTimestamp"}, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return ResponseEntity.ok(postService.getPostsByCategoryTitle(category_title, pageable));
    }

    @GetMapping(path = "/{category_title}/visible-post")
    public ResponseEntity<List<PostGetDto>> findVisiblePostsByCategoryTitle(@PathVariable("category_title") String category_title, @PageableDefault(page = 0, size = 10, sort = {"createTimestamp"}, direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return ResponseEntity.ok(postService.getVisiblePostsByCategoryTitle(category_title, pageable));
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
    public void deleteCategory(@RequestParam String category_id) {
        UUID uuid = UUID.fromString(category_id);
        categoryService.deleteCategory(uuid);
    }
}
