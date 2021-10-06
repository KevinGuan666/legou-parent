package com.kkb.legou.item.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.core.po.ResponseBean;
import com.kkb.legou.item.po.SpecGroup;
import com.kkb.legou.item.service.ISpecGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/item/group")
@CrossOrigin
public class SpecGroupController extends BaseController<ISpecGroupService, SpecGroup> {

    @ApiOperation(value = "保存规格参数", notes = "保存规格参数")
    @PostMapping("/save-group")
    public ResponseBean saveGroup(@RequestBody List<SpecGroup> specGroup) throws Exception{
        ResponseBean rm = new ResponseBean();
        try{
            if(null != specGroup && specGroup.size() > 0){
                service.saveGroup(specGroup.get(0).getCid(), specGroup);
            }
        }catch (Exception e){
            e.printStackTrace();
            rm.setSuccess(false);
            rm.setMsg("保存失败");
        }
        return rm;
    }

}
