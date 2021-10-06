package com.kkb.legou.admin.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.legou.core.po.BaseTreeEntity;
import lombok.Data;

/**
 * @Title:
 * @Description: 
 *
 * @Copyright 2019 lxs - Powered By 雪松
 * @Author: lxs
 * @Date:  2019/10/9
 * @Version V1.0
 */
@Data
@TableName("dept_")
public class Dept extends BaseTreeEntity {

	@TableField("address_")
	private String address;
	@TableField("tel_")
	private String tel;
	@TableField("desc_")
	private String desc;

	public String getLabel() { //treeselect需要的属性
		return this.getTitle();
	}

}
