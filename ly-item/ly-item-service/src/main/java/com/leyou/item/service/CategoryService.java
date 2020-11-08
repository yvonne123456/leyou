package com.leyou.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.item.dto.CategoryDTO;
import com.leyou.item.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryDTO> queryCategoryById(Long pid);

    CategoryDTO queryByID(Long id);

    List<CategoryDTO> queryCategoryByIds(List<Long> ids);


    List<CategoryDTO> queryCategoryByBrandId(Long brandId);
}
