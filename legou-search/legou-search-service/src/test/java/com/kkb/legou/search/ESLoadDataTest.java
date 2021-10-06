package com.kkb.legou.search;

import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.po.Spu;
import com.kkb.legou.search.client.BrandClient;
import com.kkb.legou.search.client.SkuClient;
import com.kkb.legou.search.client.SpuClient;
import com.kkb.legou.search.dao.GoodsDao;
import com.kkb.legou.search.po.Goods;
import com.kkb.legou.search.service.IndexService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class ESLoadDataTest {

    @Autowired
    private IndexService indexService;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SkuClient skuClient;




    @Test
    public void loadData() {
        // 查询spu
        // PageResult<SpuBO> result = this.goodsClient.querySpuByPage(page,rows, true, null);
        // List<SpuBO> spus = result.getItems();

        List<Spu> spus = spuClient.selectAll();
        // spu转为goods
        List<Goods> goods = spus.stream().map(spu -> this.indexService.buildGoods(spu)).collect(Collectors.toList()); // 把goods放入索引库

        goodsDao.saveAll(goods);
    }

    @Test
    public void loadSpu() {
        List<Spu> spus = spuClient.selectAll();
        for(Spu spu : spus){
            System.out.println(spu.getTitle());
        }
    }

    @Test
    public void loadOneSpu() {
        Spu spu = spuClient.edit(3l);
        System.out.println(spu.getTitle());
        List<Sku> skus = skuClient.selectSkusBySpuId(spu.getId());
        if(skus != null){
            for(Sku sku : skus){
                System.out.println(sku.getTitle());
            }
        }else{
            System.out.println("空");
        }


    }


}
