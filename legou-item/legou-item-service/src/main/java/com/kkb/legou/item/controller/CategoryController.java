package com.kkb.legou.item.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.item.po.Category;
import com.kkb.legou.item.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/category")
@CrossOrigin
public class CategoryController extends BaseController<ICategoryService, Category> {

    @ApiOperation(value="根据ids查询names", notes = "根据分类id查询名称列表")
    @GetMapping("/names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids){
        List<String> names = service.selectNamesByIds(ids);
        if (null == names || names.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(names);
    }
}
