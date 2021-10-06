package com.kkb.legou.item.dao;

import com.kkb.legou.core.dao.ICrudDao;
import com.kkb.legou.item.po.SpecGroup;

import java.util.List;

public interface SpecGroupDao extends ICrudDao<SpecGroup> {


    public List<SpecGroup> selectList(SpecGroup specGroup);
}
