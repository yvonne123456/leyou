package com.leyou.item.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dto.SkuDTO;
import com.leyou.item.entity.Sku;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.service.SkuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Override
    public List<SkuDTO> querySkuDTOByIds(Long spuId) {
        List<Sku> skuList = query().eq("spu_id", spuId).list();
        return SkuDTO.convertEntityList(skuList);

//        return SkuDTO.convertEntityList(query().eq("spu_id",spuId).list());
    }

    @Override

    public List<SkuDTO> querySkuBySpuListId(List<Long> ids) {

        return SkuDTO.convertEntityList(listByIds(ids));
    }



}