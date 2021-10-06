package com.kkb.legou.item.controller;

import com.kkb.legou.core.controller.BaseController;
import com.kkb.legou.item.po.SpuDetail;
import com.kkb.legou.item.service.ISpuDetailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item/spu-detail")
@CrossOrigin
public class SpuDetailController extends BaseController<ISpuDetailService, SpuDetail> {
}
