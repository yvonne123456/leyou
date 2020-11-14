package com.leyou.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.item.entity.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {

    @Select("select tb.* from tb_brand tb inner join tb_category_brand cb on tb.id = cb.brand_id where cb.category_id = #{id}")
    List<Brand> queryBrandByCategory(@Param("id") Long id);
}