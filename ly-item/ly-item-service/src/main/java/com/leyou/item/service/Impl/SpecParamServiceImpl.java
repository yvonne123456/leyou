package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.common.exception.LyException;
import com.leyou.item.dto.SpecParamDTO;
import com.leyou.item.entity.SpecParam;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.service.SpecParamService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpecParamServiceImpl extends ServiceImpl<SpecParamMapper, SpecParam> implements SpecParamService {
    @Override
    public List<SpecParamDTO> querySpecParam(Long categoryId, Long groupId, Boolean searching) {

            // 健壮性判断
            if(categoryId == null && groupId == null){
                // 条件不能都为空
                throw new LyException(400, "查询条件不能为空！");
            }
        // 查询
        List<SpecParam> list = query()
                .eq(categoryId != null, "category_id", categoryId)
                .eq(groupId != null, "group_id", groupId)
                .eq(searching != null, "searching", searching)
                .list();
        // 返回
        return SpecParamDTO.convertEntityList(list);
    }



}