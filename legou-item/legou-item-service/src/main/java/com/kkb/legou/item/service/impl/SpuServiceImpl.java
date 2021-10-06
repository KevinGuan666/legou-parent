package com.kkb.legou.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.po.Spu;
import com.kkb.legou.item.service.ISkuService;
import com.kkb.legou.item.service.ISpuDetailService;
import com.kkb.legou.item.service.ISpuService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpuServiceImpl extends CrudServiceImpl<Spu> implements ISpuService {

    @Autowired
    private ISpuDetailService spuDetailService;

    @Autowired
    private ISkuService skuService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Environment env;

    @Override
    @Transactional
    public void saveSpu(Spu spu) {
        // 保存spu
        this.saveOrUpdate(spu);

        // 保存spuDetail
        if(null == spu.getSpuDetail().getId()){
            spu.getSpuDetail().setId(spu.getId());
            spuDetailService.save(spu.getSpuDetail());
        } else{
            spuDetailService.updateById(spu.getSpuDetail());
        }

        // 保存skus
        // 删除当前spu的所有sku
        skuService.remove(Wrappers.<Sku>query().eq("spu_id_", spu.getId()));
        for(Sku sku : spu.getSkus()){
            sku.setSpuId(spu.getId());
            skuService.save(sku);
        }
        rabbitTemplate.convertAndSend(env.getProperty("mq.item.exchange.update"), env.getProperty("mq.item.routing.key"), spu.getId());


    }
}
