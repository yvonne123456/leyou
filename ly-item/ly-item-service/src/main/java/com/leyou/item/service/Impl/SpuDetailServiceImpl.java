package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dto.SpuDetailDTO;
import com.leyou.item.entity.SpuDetail;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.service.SpuDetailService;
import org.springframework.stereotype.Service;

@Service
public class SpuDetailServiceImpl extends ServiceImpl<SpuDetailMapper, SpuDetail> implements SpuDetailService {
    @Override
    public SpuDetailDTO querySpuDetailById(Long spuId) {
        return new SpuDetailDTO(getById(spuId));
    }
}