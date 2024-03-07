package com.hormonic.crowd_buying.service.category;

import com.hormonic.crowd_buying.domain.dto.response.category.CategoryResponse;
import com.hormonic.crowd_buying.domain.entity.Category;
import com.hormonic.crowd_buying.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public Category getCategoryByCategoryId(int categoryId) {
        return categoryMapper.getCategoryByCategoryId(categoryId);
    }

    public ArrayList<CategoryResponse> getCategoryListOfIdAndName() {
        return categoryMapper.getCategoryListOfIdAndName();
    }

    public ArrayList<CategoryResponse> getSubCategoryByCategoryId(int categoryId) {
        Category selectedCategory =categoryMapper.getCategoryByCategoryId(categoryId);
        return categoryMapper.getSubCategoryByCategoryId(selectedCategory.getCategoryLv(),
                                                         selectedCategory.getCategoryDetailLv());
    }

    public String getCategoryNameByCategoryId(int categoryId) {
        return categoryMapper.getCategoryNameByCategoryId(categoryId).getCategoryDetailNm();
    }

    public int getCategoryIdByDetailName(String categoryDetailName) {
        return categoryMapper.getCategoryIdByCategoryName(categoryDetailName).getCategoryId();
    }
}
