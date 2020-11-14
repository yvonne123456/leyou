package com.leyou.item.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.dto.PageDTO;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.dto.SkuDTO;
import com.leyou.item.dto.SpecParamDTO;
import com.leyou.item.dto.SpuDTO;
import com.leyou.item.dto.SpuDetailDTO;
import com.leyou.item.entity.*;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SpuDetailService detailService;
    @Autowired
    private SkuService skuService;

    @Autowired
    private  SpecParamService specParamService;

    @Override
    public PageDTO<SpuDTO> queryGoodsPage(Integer page, Integer rows, Boolean saleable, Long categoryId, Long brandId, Long id) {

        Page<Spu> spuPage = new Page<>(page, rows);

        page(spuPage, new QueryWrapper<Spu>()
                .eq(null != categoryId, "cid3", categoryId)
                .eq(null != brandId, "brand_id", brandId)
                .eq(id != null, "id", id)
                .eq(null != saleable, "saleable", saleable));

        List<SpuDTO> spuDTOS = SpuDTO.convertEntityList(spuPage.getRecords());

        for (SpuDTO spuDTO : spuDTOS) {
            //查询到分类的集合，并取到其名称并转换为字符串
            String names = this.categoryService
                    .listByIds(spuDTO.getCategoryIds())
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.joining("/"));
            spuDTO.setCategoryName(names);

            spuDTO.setBrandName(this.brandService.getById(spuDTO.getBrandId()).getName());
        }

        return new PageDTO<>(spuPage.getTotal(), spuPage.getPages(), spuDTOS);
    }

    @Override
    public void addGoods(SpuDTO spuDTO) {
        Spu spu = spuDTO.toEntity(Spu.class);

        this.save(spu);

        SpuDetail spuDetail = spuDTO.getSpuDetail().toEntity(SpuDetail.class);

        spuDetail.setSpuId(spu.getId());

        this.detailService.save(spuDetail);
        ///迭代skuDTO依次转换为sku并返回
        List<Sku> skus = spuDTO.getSkus().stream().map(skuDTO -> {
                    Sku sku = new Sku();
                    //拷贝skuDto sku 对象
                    BeanUtils.copyProperties(skuDTO, sku);
                    sku.setId(spu.getId());
                    sku.setSaleable(true);
                    return sku;
                }
        ).collect(Collectors.toList());

        this.skuService.saveBatch(skus);
    }

    @Override
    public void updateGoods(SpuDTO spuDTO) {

        if(null!=spuDTO.getId()) {
            Spu spu = spuDTO.toEntity(Spu.class);
            spu.setUpdateTime(new Date());
            this.updateById(spu);
        }

        if (null!=spuDTO.getSpuDetail()) {
            SpuDetail spuDetail = spuDTO.getSpuDetail().toEntity(SpuDetail.class);
            this.detailService.updateById(spuDetail);

        }

        if (null!=spuDTO.getSkus()&& 0!=spuDTO.getSkus().size()){

            List<Sku> skus = spuDTO.getSkus().stream().map(skuDTO -> {
                Sku sku = new Sku();
                BeanUtils.copyProperties(skuDTO, sku);
                return sku;
            }).collect(Collectors.toList());

            //key 的值有true和false之分，如果key的值为true，表示修改或新增，false表示要下架删除的
            Map<Boolean, List<Sku>> skuMaps = skus.stream().collect(Collectors.groupingBy(sku -> {
                return sku.getSaleable() == null;
            }));

            List<Sku> toUpdateOrSave = skuMaps.get(true);

            //新增或修改，区别在于id此sku的id存在就修改，否则新增
            this.skuService.saveOrUpdateBatch(toUpdateOrSave);

            List<Sku> toDelete = skuMaps.get(false);

            //根据id批量删除
            this.skuService.removeByIds(toDelete.stream().map(Sku::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public void updateSaleable(Long id, Boolean saleable) {

        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(saleable);
        this.updateById(spu);

        Sku sku = new Sku();
        sku.setId(id);
        sku.setSaleable(saleable);

        this.skuService.update(sku,new QueryWrapper<Sku>().eq("spu_id",id));
    }

    @Override
    public SpuDTO queryGoodsById(Long id) {


        Spu spu = getById(id);
        SpuDTO spuDTO = new SpuDTO(spu);

        if (spu == null) {
            throw new LyException(400, "商品id不存在！");
        }

        SpuDetail detail = detailService.getById(id);
        if (detail == null) {
            throw new LyException(400, "商品id不存在！");
        }
        spuDTO.setSpuDetail(new SpuDetailDTO(detail));

        List<Sku> list = skuService.query().eq("spu_id", id).list();
        if(CollectionUtils.isEmpty(list)){

            throw new LyException(400, "商品id不存在！");
        }
        spuDTO.setSkus(SkuDTO.convertEntityList(list));
        // 根据品牌id查询品牌名称

        Brand brand = brandService.getById(spuDTO.getBrandId());
        if(brand != null) {
            spuDTO.setBrandName(brand.getName());
    }
        return spuDTO;
    }

    @Override
    public List<SpecParamDTO> querySpecsValues(Long id, Boolean searching) {

        Long categoryId = getById(id).getCid3();

        List<SpecParamDTO> specParamDTOS = SpecParamDTO.convertEntityList(this.specParamService.query()
                .eq("category_id", categoryId)
                .eq(null != searching, "searching", searching).list());

        Map<Long,Object> paramMap = JsonUtils.nativeRead(this.detailService.getById(id).getSpecification(), new TypeReference<Map<Long, Object>>() {
        }) ;

        specParamDTOS.forEach(specParamDTO -> {

            Long specPramId = specParamDTO.getId();
            specParamDTO.setValue(paramMap.get(specPramId));

        });


        return specParamDTOS;
    }
}


