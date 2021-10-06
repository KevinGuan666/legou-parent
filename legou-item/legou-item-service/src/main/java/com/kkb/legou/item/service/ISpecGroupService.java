package com.kkb.legou.item.service;

import com.kkb.legou.core.service.ICrudService;
import com.kkb.legou.item.po.SpecGroup;

import java.util.List;

public interface ISpecGroupService extends ICrudService<SpecGroup> {

    /**
     * 根据前台传递的规格参数列表，保存规格参数 * @param cid
     * @param groups
     */
    public void saveGroup(Long cid, List<SpecGroup> groups);
}
