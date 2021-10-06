package com.kkb.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.item.dao.BrandDao;
import com.kkb.legou.item.po.Brand;
import com.kkb.legou.item.po.Category;
import com.kkb.legou.item.service.IBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl extends CrudServiceImpl<Brand> implements IBrandService {

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Brand entity) {
        boolean result = super.saveOrUpdate(entity);
        ((BrandDao) getBaseMapper()).deleteCategoryByBrand(entity.getId()); //删除商品和分类的关联
        //添加商品和分类的关联
        Long[] roleIds = entity.getCategoryIds();
        if (null != roleIds) {
            for (Long roleId : roleIds) {
                ((BrandDao) getBaseMapper()).insertCategoryAndBrand(roleId, entity.getId());
            }
        }
        return result;
    }

    @Override
    public List<Category> selectCategoryByBrand(Long id) {
        return ((BrandDao) getBaseMapper()).selectCategoryByBrand(id);
    }

    @Override
    public List<Brand> selectBrandByIds(List<Long> ids) {
        QueryWrapper<Brand> queryWrapper =  Wrappers.<Brand>query().in("id_", ids);
        return getBaseMapper().selectList(queryWrapper);
    }
}
