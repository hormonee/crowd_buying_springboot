package com.hormonic.crowd_buying.service.category;

import com.hormonic.crowd_buying.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {
    public ArrayList<Category> getCategoryList();

    public Category getCategoryByCategoryId(int categoryId);
}
