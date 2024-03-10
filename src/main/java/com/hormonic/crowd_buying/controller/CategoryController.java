package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.response.category.CategoryResponse;
import com.hormonic.crowd_buying.domain.entity.Category;
import com.hormonic.crowd_buying.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "모든 카테고리 정보 조회", description = "카테고리 ID와 이름을 조회")
    public ResponseEntity<List<CategoryResponse>> getCategoryListOfIdAndName() {
        return ResponseEntity.ok(categoryService.getCategoryListOfIdAndName());
    }

    @GetMapping("/{id}")
    @Operation(summary = "카테고리 세부 정보 조회", description = "카테고리 ID를 통해 카테고리 세부 정보 조회")
    public ResponseEntity<Category> getCategoryByCategoryId(@PathVariable("id") int categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryByCategoryId(categoryId));
    }

    @GetMapping("/{id}/sub")
    @Operation(summary = "서브 카테고리 조회", description = "카테고리 ID를 통해 해당 카테고리의 서브 카테고리 전부 조회")
    public ResponseEntity<List<CategoryResponse>> getSubCategoryByCategoryId(@PathVariable("id") int categoryId) {
        return ResponseEntity.ok(categoryService.getSubCategoryByCategoryId(categoryId));
    }

}
