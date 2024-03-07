package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.response.category.CategoryResponse;
import com.hormonic.crowd_buying.domain.entity.Category;
import com.hormonic.crowd_buying.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategoryListOfIdAndName() {
        return ResponseEntity.ok(categoryService.getCategoryListOfIdAndName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryByCategoryId(@PathVariable("id") int categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryByCategoryId(categoryId));
    }

    @GetMapping("/{id}/sub")
    public ResponseEntity<List<CategoryResponse>> getSubCategoryByCategoryId(@PathVariable("id") int categoryId) {
        return ResponseEntity.ok(categoryService.getSubCategoryByCategoryId(categoryId));
    }

}
