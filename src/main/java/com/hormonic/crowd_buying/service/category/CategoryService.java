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

    // 카테고리 ID를 통해 해당 카테고리 세부 정보 출력
    public Category getCategoryByCategoryId(int categoryId) {
        return categoryMapper.getCategoryByCategoryId(categoryId);
    }

    // 모든 카테고리에 대해 ID와 이름만 묶어서 리스트로 출력
    public ArrayList<CategoryResponse> getCategoryListOfIdAndName() {
        return categoryMapper.getCategoryListOfIdAndName();
    }

    // 카테고리의 하위 카테고리들을 출력
    public ArrayList<CategoryResponse> getSubCategoryByCategoryId(int categoryId) {
        Category selectedCategory =categoryMapper.getCategoryByCategoryId(categoryId);
        return categoryMapper.getSubCategoryByCategoryId(selectedCategory.getCategoryLv(),
                                                         selectedCategory.getCategoryDetailLv());
    }

    // 카테고리 ID와 매칭되는 카테고리명 반환
    public String getCategoryNameByCategoryId(int categoryId) {
        return categoryMapper.getCategoryNameByCategoryId(categoryId).getCategoryDetailNm();
    }

    // 카테고리명과 매칭되는 카테고리 ID 반환
    public int getCategoryIdByDetailName(String categoryDetailName) {
        return categoryMapper.getCategoryIdByCategoryName(categoryDetailName).getCategoryId();
    }
}
