package com.kkb.legou.search.dao;

import com.kkb.legou.search.po.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {
}
