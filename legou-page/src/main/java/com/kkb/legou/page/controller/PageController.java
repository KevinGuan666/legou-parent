package com.kkb.legou.page.controller;

import com.kkb.legou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageService pageService;

    /**
     * 生成静态页面
     * @param id SPU的ID
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    public ResponseEntity<String> createHtml(@PathVariable(name="id") Long id){
        pageService.createPageHtml(id);
        return ResponseEntity.ok("生成成功");
    }
}
