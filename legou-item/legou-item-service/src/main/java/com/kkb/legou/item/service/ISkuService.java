package com.kkb.legou.item.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.item.po.Sku;

public interface ISkuService extends ICrudService<Sku> {


    /**
     * 减库存
     * @param num
     * @param skuId
     */
    public void decrCount(Integer num, Long skuId);

    /**
     * 回滚库存
     * @param num
     * @param skuId
     */
    public void rollBackCount(Integer num, Long skuId);
}
