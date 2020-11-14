package com.leyou.item.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.common.dto.PageDTO;
import com.leyou.item.dto.BrandDTO;
import com.leyou.item.entity.Brand;
import com.leyou.item.entity.CategoryBrand;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.service.BrandService;
import com.leyou.item.service.CategoryBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Autowired
    private BrandService brandService;


    @Override
    public PageDTO<BrandDTO> queryPageBrand(Integer page, Integer rows, String key) {

        Page queryPage = new Page(page, rows);
        //不能为空
        boolean isKeyExists = StringUtils.isNoneBlank(key);

        //按条件查找
        page(queryPage, new QueryWrapper<Brand>()
                .like(isKeyExists, "name", key)
                .or()
                .like(isKeyExists, "letter", key)
        );

        //封装结果
        List<Brand> list = queryPage.getRecords();
        return new PageDTO<BrandDTO>(queryPage.getTotal(), queryPage.getPages(), BrandDTO.convertEntityList(list));
    }

    @Override
    public void addBrand(BrandDTO brandDTO) {
        Brand brand = brandDTO.toEntity(Brand.class);

        this.save(brand);

        List<CategoryBrand> categoryBrandList = new ArrayList<>();

        for (Long categoryId : brandDTO.getCategoryIds()) {

            categoryBrandList.add(CategoryBrand.of(categoryId, brand.getId()));

        }
        this.categoryBrandService.saveBatch(categoryBrandList);
    }


    @Override
    public void updateBranById(BrandDTO brandDTO) {
        //dto装换po
        Brand brand = brandDTO.toEntity(Brand.class);
        //根据id更新
        this.updateById(brand);
        this.categoryBrandService.remove(new QueryWrapper<CategoryBrand>().eq("brand_id",brand.getId()));
    }


    @Override
    public BrandDTO queryBrandById(Long id) {

        return new BrandDTO(getById(id));

    }
    @Override
    public List<BrandDTO> queryBrandByIds(List<Long> ids) {
        return BrandDTO.convertEntityList(this.listByIds(ids));
    }

    @Override
    public void DeleteBrandById(Long id) {

        this.removeById(id);

        this.categoryBrandService.remove(new QueryWrapper<CategoryBrand>()
                .eq("brand_id",id));
    }

    @Override
    public List<BrandDTO> queryBrandByCategory(Long id) {
        List<Brand> list = getBaseMapper().queryBrandByCategory(id);
        return BrandDTO.convertEntityList(list);
    }


}