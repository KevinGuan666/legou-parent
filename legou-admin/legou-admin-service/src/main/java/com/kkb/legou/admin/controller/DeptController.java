package com.kkb.legou.admin.controller;

import com.kkb.legou.admin.po.Dept;
import com.kkb.legou.admin.service.IDeptService;
import com.kkb.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController<IDeptService, Dept> {

}
