package com.kkb.legou.item.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.item.po.Brand;
import com.kkb.legou.item.po.Category;

import java.util.List;

public interface IBrandService extends ICrudService<Brand> {

    /**
     * 根据商品id查询分类 * @param id
     * @return
     */
    public List<Category> selectCategoryByBrand(Long id);


    /**
     * 根据品牌id集合，得到品牌集合
     * @param ids
     */
    public List<Brand> selectBrandByIds(List<Long> ids);

}
