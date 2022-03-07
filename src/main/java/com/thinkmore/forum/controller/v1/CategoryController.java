package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
}
