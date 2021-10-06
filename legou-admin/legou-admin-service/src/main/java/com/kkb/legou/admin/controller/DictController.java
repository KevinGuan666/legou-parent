package com.kkb.legou.admin.controller;

import com.kkb.legou.admin.po.Dict;
import com.kkb.legou.admin.service.IDictService;
import com.kkb.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController<IDictService, Dict> {


}
