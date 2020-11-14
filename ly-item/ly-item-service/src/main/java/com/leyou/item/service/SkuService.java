package com.leyou.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.item.dto.SkuDTO;
import com.leyou.item.entity.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {
    List<SkuDTO> querySkuDTOByIds(Long spuId);

    List<SkuDTO> querySkuBySpuListId(List<Long> ids);

}