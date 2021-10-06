package com.kkb.legou.item.api;

import com.kkb.legou.item.po.SpuDetail;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/item/spu-detail")
public interface SpuDetailApi {

    /**
     * 加载
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="加载", notes="根据ID加载")
    @GetMapping("/edit/{id}")
    public SpuDetail edit(@PathVariable Long id);

}