package com.hormonic.crowd_buying.service.category;

import com.hormonic.crowd_buying.domain.entity.Category;
import com.hormonic.crowd_buying.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public ArrayList<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public Category getCategoryByCategoryId(int categoryId) {
        return categoryMapper.getCategoryByCategoryId(categoryId);
    }

    public int getCategoryIdByDetailName(String detailName) {
        return categoryRepository.findByCategoryDetailNm(detailName).getCategoryId();
    }
}
