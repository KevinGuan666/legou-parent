package com.kkb.legou.page.service.impl;


import com.kkb.legou.common.utils.JsonUtils;
import com.kkb.legou.core.json.JSON;
import com.kkb.legou.item.po.Category;
import com.kkb.legou.item.po.Sku;
import com.kkb.legou.item.po.Spu;
import com.kkb.legou.item.po.SpuDetail;
import com.kkb.legou.page.client.CategoryClient;
import com.kkb.legou.page.client.SkuClient;
import com.kkb.legou.page.client.SpuClient;
import com.kkb.legou.page.client.SpuDetailClient;
import com.kkb.legou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuDetailClient spuDetailClient;

    @Autowired
    private TemplateEngine templateEngine;

    //生成静态文件路径
    @Value("${pagepath}")
    private String pagepath;


    /**
     * 构建数据模型
     * @param spuId
     * @return
     */
    private Map<String,Object> buildDataModel(Long spuId) {
        //构建数据模型
        Map<String, Object> dataMap = new HashMap<>();
        //获取spu 和SKU列表
        Spu spu = spuClient.edit(spuId);
        //获取分类信息
        dataMap.put("category1", categoryClient.edit(spu.getCid1()));
        dataMap.put("category2", categoryClient.edit(spu.getCid2()));
        dataMap.put("category3", categoryClient.edit(spu.getCid3()));

        List<Sku> skus = skuClient.selectSkusBySpuId(spu.getId());
        List<String> images = new ArrayList<>();
        for (Sku sku : skus) {
            images.add(sku.getImages());
        }
        dataMap.put("imageList", images);

        SpuDetail spuDetail = spuDetailClient.edit(spu.getId());
        // Map<String, String> genericMap = JsonUtils.parseMap(spuDetail.getGenericSpec(), String.class, String.class);
        Map<String, Object> genericMap = JsonUtils.parseMap(spuDetail.getSpecialSpec(), String.class, Object.class);
        // dataMap.put("specificationList", JSON.parseObject(spu.getSpecItems(),Map.class));
        dataMap.put("specificationList", genericMap);
        dataMap.put("spu",spu);
        dataMap.put("spuDetail",spuDetail);
        //根据spuId查询Sku集合
        dataMap.put("skuList", skus);
        return dataMap;
    }


    /***
     * 生成静态页
     * @param spuId
     */
    @Override
    public void createPageHtml(Long spuId) {
        // 1.上下文 模板 + 数据集 =html
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(spuId);
        context.setVariables(dataModel);//model.addtribute("key", "value") ${key}
        // 2.准备文件
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, spuId + ".html");
        // 3.生成页面
        try{
            PrintWriter writer = new PrintWriter(dest, "UTF-8");
            //模板的文件
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
