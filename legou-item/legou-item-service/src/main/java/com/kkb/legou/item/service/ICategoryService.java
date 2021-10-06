package com.kkb.legou.item.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.item.po.Category;

import java.util.List;

public interface ICategoryService extends ICrudService<Category> {

    public List<String> selectNamesByIds(List<Long> ids);
}
