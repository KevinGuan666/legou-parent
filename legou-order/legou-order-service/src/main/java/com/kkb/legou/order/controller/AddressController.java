package com.kkb.legou.order.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.order.config.TokenDecode;
import com.kkb.legou.order.po.Address;
import com.kkb.legou.order.service.IAddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/address")
public class AddressController extends BaseController<IAddressService, Address> {

    @Autowired
    private TokenDecode tokenDecode;


    @Override
    @ApiOperation(value="查询", notes="根据实体条件查询")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public List<Address> list(Address entity) {
        String username = null;
        try {
            username = tokenDecode.getUserInfo().get("user_name");
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity.setUsername(username);

        return service.list(entity);
    }

}
