package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dto.CategoryDTO;
import com.leyou.item.entity.CategoryBrand;
import com.leyou.item.mapper.CategoryBrandMapper;
import com.leyou.item.service.CategoryBrandService;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {


    @Autowired
    private CategoryService categoryService;


    /**
     *
     * @param bid
     * @return categoryIds
     */
    @Override
    public List<CategoryDTO> queryCategoryByBrandId(Long bid) {

       List<Long> categoryIds = query().eq("brand_id", bid)
               .list().stream()
               .map(CategoryBrand::getCategoryId)
               .collect(Collectors.toList());

        return  CategoryDTO.convertEntityList(this.categoryService.listByIds(categoryIds));

    }

}