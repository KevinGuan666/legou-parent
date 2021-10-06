package com.kkb.legou.item.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.service.ISkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/sku")
@CrossOrigin
public class SkuController extends BaseController<ISkuService, Sku> {

    @ApiOperation(value="查询spu对应的sku", notes="根据spuId查询sku集合")
    @GetMapping("/select-skus-by-spuid/{id}")
    public List<Sku> selectSkusBySpuId(@PathVariable("id") Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        return service.list(sku);
    }


    /**
     * 减库存
     * @param num
     * @param skuId */
    @PostMapping(value = "/decr-count")
    public void decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId) {
        service.decrCount(num, skuId);
    }

    /**
     * 回滚库存
     * @param num
     * @param skuId */
    @PostMapping(value = "/roll-count")
    public void rollBackCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId) {
        service.rollBackCount(num, skuId);
    }
}
