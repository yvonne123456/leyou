package com.leyou.item.web;

import com.leyou.common.dto.PageDTO;
import com.leyou.item.dto.BrandDTO;
import com.leyou.item.service.BrandService;
import com.leyou.item.service.CategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;
    @Autowired
    private BrandService brandService;
    //分页查询品牌
    @GetMapping("page")
    public ResponseEntity<PageDTO<BrandDTO>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "key", required = false)String key

    ){
        return ResponseEntity.ok(brandService.queryPageBrand(page,rows,key));
    }
    //新增品牌
    @PostMapping
    public ResponseEntity<Void> addBrand(BrandDTO brandDTO){

        this.brandService.addBrand(brandDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    //根据分类id查询品牌
    @GetMapping("/of/category")
    public ResponseEntity<List<BrandDTO>> queryBrandByCategoryById(@RequestParam("id") Long cid){

        return ResponseEntity.ok(this.categoryBrandService.queryBrandByCategoryById(cid));

    }
    //更新品牌
    @PutMapping
    public  ResponseEntity<Void> updateBranById(BrandDTO brandDTO){

        this.brandService.updateBranById(brandDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    //根据id查询品牌
    @GetMapping("/{id}")//根据id查询需要用@PathVariable
    public ResponseEntity<BrandDTO> queryBrandById(@PathVariable("id") Long id){

        return ResponseEntity.ok(this.brandService.queryBrandById(id));

    }
    //根据品牌id集合查询品牌集合
    @GetMapping("list")
    public ResponseEntity<List<BrandDTO>> queryBrandByIds(
            @RequestParam("ids")List<Long> ids){
        return ResponseEntity.ok(this.brandService.queryBrandByIds(ids));
    }

    //根据id删除品牌
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteBrandById(
            @PathVariable("id")Long id){
        this.brandService. DeleteBrandById(id);
        return ResponseEntity.ok().build();
    }
    }


