package com.leyou.item.web;

import com.leyou.item.dto.CategoryDTO;
import com.leyou.item.service.CategoryBrandService;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryBrandService categoryBrandService;


    //根据父类目id，查询子类目的集合
    @GetMapping("/of/parent")
    public ResponseEntity<List<CategoryDTO>> queryCategoryById(@RequestParam("pid") Long pid) {

        return ResponseEntity.ok(this.categoryService.queryCategoryById(pid));

    }



    //根据分类id，查询商品分类
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> queryById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(this.categoryService.queryByID(id));

    }
    //根据分类id的集合，查询商品分类的集合
    @GetMapping("list")
    public ResponseEntity<List<CategoryDTO>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        List<CategoryDTO> categoryDTOS =categoryService.queryCategoryByIds(ids);
        return ResponseEntity.ok(categoryDTOS);
    }

//    @GetMapping("/of/brand")
//    public ResponseEntity<List<CategoryDTO>> queryCategoryByBrandId(@RequestParam("id") Long brandId){
//        return ResponseEntity.ok(categoryService.queryCategoryByBrandId(brandId));
//    }
//

        //根据品牌id，查询商品分类的集合
        @GetMapping("/of/brand")
        public ResponseEntity<List<CategoryDTO>> queryCategoryByBrandId(@RequestParam("id") Long bid){
        return ResponseEntity.ok(this.categoryBrandService.queryCategoryByBrandId(bid));
    }
    }




