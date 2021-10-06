package com.kkb.legou.item.dao;

import com.kkb.legou.core.dao.ICrudDao;
import com.kkb.legou.item.po.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SkuDao extends ICrudDao<Sku> {

    @Select("select * from sku_ where spu_id_ = #{spuId}")
    public List<Sku> findBySpuId(Integer spuId);


    @Update(value="update sku_ set stock_ = stock_ - #{num} where id_ =#{skuId} and stock_ >= #{num}")
    public int decrCount(@Param("num") Integer num, @Param("skuId") Long skuId);

    @Update(value="update sku_ set stock_ = stock_ + #{num} where id_ =#{skuId}")
    public int rollBackStock(@Param("num") Integer num, @Param("skuId") Long skuId);
}
