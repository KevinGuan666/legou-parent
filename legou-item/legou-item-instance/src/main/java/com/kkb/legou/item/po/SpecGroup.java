package com.kkb.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kkb.legou.core.po.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@TableName("spec_group_")
@JsonIgnoreProperties(value = {"handler"})
public class SpecGroup extends BaseEntity {

    @TableField("cid_")
    private long cid;

    @TableField("name_")
    private String name;

    @TableField(exist = false)
    private List<SpecParam> params; // 该组下的所有规格参数集合

}
