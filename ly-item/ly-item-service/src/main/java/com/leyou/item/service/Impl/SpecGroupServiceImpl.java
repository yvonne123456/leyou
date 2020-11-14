package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dto.SpecGroupDTO;
import com.leyou.item.entity.SpecGroup;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.service.SpecGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecGroupServiceImpl extends ServiceImpl<SpecGroupMapper, SpecGroup> implements SpecGroupService {
    @Override
    public List<SpecGroupDTO> queryGroupByCategory(Long id) {

//        List<SpecGroupDTO> specGroupDTOS = query().eq("category_id", id)
//                .list().stream()
//                .map(SpecGroupDTO::new)
//                .collect(Collectors.toList());
//        return specGroupDTOS;


        List<SpecGroup> list = getBaseMapper().queryByCategoryId(id);
        return SpecGroupDTO.convertEntityList(list);
    }

}