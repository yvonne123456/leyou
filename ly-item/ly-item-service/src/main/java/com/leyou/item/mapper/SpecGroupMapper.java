package com.leyou.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyou.item.entity.SpecGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecGroupMapper extends BaseMapper<SpecGroup> {
    @Select("select  *  from tb_category tcy inner join tb_spec_group tsg on tcy.id = tsg.id where category_id= #{id};")
    List<SpecGroup> queryByCategoryId(@Param("id") Long id);
}