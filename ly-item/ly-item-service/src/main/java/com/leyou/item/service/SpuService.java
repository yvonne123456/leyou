package com.leyou.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.common.dto.PageDTO;
import com.leyou.item.dto.SpecParamDTO;
import com.leyou.item.dto.SpuDTO;
import com.leyou.item.entity.SpecParam;
import com.leyou.item.entity.Spu;

import java.util.List;

public interface SpuService extends IService<Spu> {
    PageDTO<SpuDTO> queryGoodsPage(Integer page, Integer rows, Boolean saleable, Long categoryId, Long brandId, Long id);

    void addGoods(SpuDTO spuDTO);

    void updateGoods(SpuDTO spuDTO);

    void updateSaleable(Long id, Boolean saleable);

    SpuDTO queryGoodsById(Long id);

    List<SpecParamDTO> querySpecsValues(Long id, Boolean searching);
}