package com.kkb.legou.item.api;

import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.po.Spu;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/item/sku")
public interface SkuApi {

    @ApiOperation(value="查询spu对应的sku", notes = "根据spuId查询sku集合")
    @GetMapping("/select-skus-by-spuid/{id}")
    public List<Sku> selectSkusBySpuId(@PathVariable("id") Long spuId);


    @ApiOperation(value="加载", notes = "根据ID加载")
    @GetMapping("/edit/{id}")
    public Sku edit(@PathVariable Long id);

    @PostMapping(value = "/decr-count")
    public void decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId);

    @PostMapping(value = "/roll-count")
    public void rollBackCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId);
}
