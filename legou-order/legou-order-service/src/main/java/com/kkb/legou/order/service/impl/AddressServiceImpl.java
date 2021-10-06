package com.kkb.legou.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kkb.legou.core.service.impl.CrudServiceImpl;
import com.kkb.legou.order.po.Address;
import com.kkb.legou.order.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends CrudServiceImpl<Address> implements IAddressService {

    @Override
    public List<Address> list(Address entity) {
        //根据用户名查询用户收货地址
        QueryWrapper<Address> queryWrapper = Wrappers.<Address>query();
        if (StringUtils.isNotEmpty(entity.getUsername())) {
            queryWrapper.eq("username_", entity.getUsername());
        }
        return getBaseMapper().selectList(queryWrapper);
    }
}
