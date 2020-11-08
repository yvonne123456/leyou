package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dto.CategoryDTO;
import com.leyou.item.entity.Category;
import com.leyou.item.entity.CategoryBrand;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.service.CategoryBrandService;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Override
    public List<CategoryDTO> queryCategoryById(Long pid) {

        List<Category>categoryList= query()
                .eq("parent_id", pid).list();

        //entity装换成DTO
        return  CategoryDTO.convertEntityList(categoryList);
    }

    @Override
    public CategoryDTO queryByID(Long id) {

     return new CategoryDTO(getById(id));
    }

    @Override
    public List<CategoryDTO> queryCategoryByIds(List<Long> ids) {

        return CategoryDTO.convertEntityList(query().in("id", ids).list());
//        return ids.stream().map(this::queryByID).collect(Collectors.toList());
//        List<Category> list =new ArrayList<>();
//        for (Long id : ids) {
//            QueryWrapper<Category> queryWrapper =new QueryWrapper<>();
//            queryWrapper.eq("id", id);
//            Category category = getOne(queryWrapper);
//            list.add(category);
//        }
//        List<CategoryDTO> categoryDTOS = CategoryDTO.convertEntityList(list);
//        return categoryDTOS;
    }

    @Override
    public List<CategoryDTO> queryCategoryByBrandId(Long brandId) {
        // 1.根据品牌id，查询中间表，得到中间表对象集合
        List<CategoryBrand> categoryBrandList = categoryBrandService.query()
                .eq("brand_id", brandId)
                .list();
        if (CollectionUtils.isEmpty(categoryBrandList)){

            return Collections.emptyList();
        }
        // 2.获取分类id集合
        List<Long> list = categoryBrandList.stream()
                .map(CategoryBrand::getCategoryId)
                .collect(Collectors.toList());
        // 3.根据分类id集合，查询分类对象集合
        List<Category> categoryList = listByIds(list);
        // 4.转换DTO
        return CategoryDTO.convertEntityList(categoryList);
    }

}

