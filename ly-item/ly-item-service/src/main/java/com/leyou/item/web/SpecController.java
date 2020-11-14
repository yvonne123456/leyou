package com.leyou.item.web;

import com.leyou.item.dto.SpecGroupDTO;
import com.leyou.item.dto.SpecParamDTO;
import com.leyou.item.service.SpecGroupService;
import com.leyou.item.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;
    @Autowired
    private SpecParamService specParamService;
    //根据分类查询规格组
    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> queryGroupByCategory(@RequestParam("id") Long id) {

      return ResponseEntity.ok(this.specGroupService.queryGroupByCategory(id));
    }


    //根据条件查询规格参数集合
    @GetMapping("params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParam(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "groupId", required = false) Long groupId,
            @RequestParam(value = "searching", required = false) Boolean searching
         ){
        return ResponseEntity.ok(this.specParamService.querySpecParam(categoryId,groupId,searching));
    }
}

