package com.leyou.item.web;

import com.leyou.common.dto.PageDTO;
import com.leyou.item.dto.SkuDTO;
import com.leyou.item.dto.SpecParamDTO;
import com.leyou.item.dto.SpuDTO;
import com.leyou.item.dto.SpuDetailDTO;
import com.leyou.item.service.SkuService;
import com.leyou.item.service.SpecParamService;
import com.leyou.item.service.SpuDetailService;
import com.leyou.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private SpuDetailService spuDetailService;

    @Autowired
    private SpecParamService specParamService;

    //商品分页查询
    @GetMapping("/spu/page")
    public ResponseEntity<PageDTO<SpuDTO>> queryGoodsPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(spuService.queryGoodsPage(page, rows, saleable, categoryId, brandId, id));
    }

    //商品新增
    @PostMapping("/spu")
    public ResponseEntity<Void> addGoods(@RequestBody SpuDTO spuDTO) {
        this.spuService.addGoods(spuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //商品修改
    @PutMapping("/spu")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuDTO spuDTO) {

        this.spuService.updateGoods(spuDTO);
        return ResponseEntity.ok().build();
    }

    //修改商品上架下架
    @PutMapping("/saleable")
    public ResponseEntity<Void> updateSpuSaleable(
            @RequestParam("id") Long id,
            @RequestParam("saleable") Boolean saleable) {
        spuService.updateSaleable(id, saleable);
        return ResponseEntity.ok().build();
    }

    //根据id查询商品
    @GetMapping("/{id}")
    public ResponseEntity<SpuDTO> queryGoodsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(spuService.queryGoodsById(id));
    }

    //根据id查询SpuDetail
    @GetMapping("/spu/detail")
    public ResponseEntity<SpuDetailDTO> querySpuDetailById(@RequestParam("id") Long spuId) {

        return ResponseEntity.ok(this.spuDetailService.querySpuDetailById(spuId));
    }
    //根据SpuId查询SKU集合

    @GetMapping("/spu//of/spu")
    public ResponseEntity<List<SkuDTO>> querySkuDTOByIds(@RequestParam("id") Long spuId) {

        return ResponseEntity.ok(this.skuService.querySkuDTOByIds(spuId));
    }

    //根据id集合查询SKU集合
    @GetMapping("/spu/list")
    public ResponseEntity<List<SkuDTO>> querySkuBySpuListId(@RequestParam("id") List<Long> ids) {

        return ResponseEntity.ok(this.skuService.querySkuBySpuListId(ids));
    }

    //查询商品规格参数键值对
    @GetMapping("/spec/value")
    public ResponseEntity<List<SpecParamDTO>> querySpecsValues(
            @RequestParam("id") Long id,
            @RequestParam(value = "searching", required = false) Boolean searching
    ) {
        return ResponseEntity.ok(this.spuService.querySpecsValues(id, searching));
    }
}


