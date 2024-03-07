package com.hormonic.crowd_buying.service.category;

import com.hormonic.crowd_buying.domain.dto.response.category.CategoryResponse;
import com.hormonic.crowd_buying.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {
    public ArrayList<CategoryResponse> getCategoryListOfIdAndName();

    public ArrayList<CategoryResponse> getSubCategoryByCategoryId(@Param("categoryLv") int categoryLv,
                                                                  @Param("categoryDetailLv") int categoryDetailLv);

    public Category getCategoryByCategoryId(int categoryId);

    public CategoryResponse getCategoryNameByCategoryId(int categoryId);

    public CategoryResponse getCategoryIdByCategoryName(String categoryDetailName);
}
