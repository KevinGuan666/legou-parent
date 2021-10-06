package com.kkb.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.item.dao.SkuDao;
import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.service.ISkuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
public class SkuServiceImpl extends CrudServiceImpl<Sku> implements ISkuService{

    @Override
    public void decrCount(Integer num, Long skuId) {
        ((SkuDao) getBaseMapper()).decrCount(num, skuId);
    }

    @Override
    public void rollBackCount(Integer num, Long skuId) {
        ((SkuDao) getBaseMapper()).rollBackStock(num, skuId);
    }

    public List<Sku> list(Sku entity){
        QueryWrapper<Sku> queryWrapper = Wrappers.<Sku>query();
        if(entity.getSpuId() != null){
            queryWrapper.eq("spu_id_", entity.getSpuId());
        }
        return getBaseMapper().selectList(queryWrapper);




    }
}
