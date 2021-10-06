package com.kkb.legou.item.controller;


import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.item.po.Brand;
import com.kkb.legou.item.po.Category;
import com.kkb.legou.item.service.IBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/brand")
@CrossOrigin
public class BrandController extends BaseController<IBrandService, Brand> {

    @Override
    public void afterEdit(Brand domain) {
        //生成角色列表, 如:1,3,4
        List<Category> categories = service.selectCategoryByBrand(domain.getId());
        Long[] ids = new Long[categories.size()];
        for (int i=0; i< categories.size(); i++) {
            ids[i] = categories.get(i).getId();
        }
        domain.setCategoryIds(ids);
    }

    @ApiOperation(value="根据ids查询", notes = "根据ids查询")
    @GetMapping("/list-by-ids")
    public List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids) {
        return service.selectBrandByIds(ids);
    }
}
