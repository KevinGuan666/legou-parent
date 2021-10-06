package com.kkb.legou.page.listener;

import com.kkb.legou.page.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ItemUpdateListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(queues = "${mq.item.queue.update}")
    public void handlerData(Long id){
        pageService.createPageHtml(id);
    }

}


