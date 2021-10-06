package com.kkb.legou.search.service;

import com.kkb.legou.item.po.Brand;
import com.kkb.legou.item.po.Category;
import com.kkb.legou.item.po.SpecParam;
import com.kkb.legou.search.client.BrandClient;
import com.kkb.legou.search.client.CategoryClient;
import com.kkb.legou.search.client.SpecParamClient;
import com.kkb.legou.search.dao.GoodsDao;
import com.kkb.legou.search.po.Goods;
import com.kkb.legou.search.po.SearchRequest;
import com.kkb.legou.search.po.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    private GoodsDao goodsRepository;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecParamClient specificationClient;

    public SearchResult search(SearchRequest searchRequest){
        String key = searchRequest.getKey();
        if(key == null){
            return null;
        }

        //查询构建工具
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //添加了查询的过滤，只要这些字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[] {"id","subTitle","skus"},null));

        //构造布尔查询
        BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(searchRequest);

        //把查询条件添加到构建器中(这里仅仅是我的查询条件)
        queryBuilder.withQuery(boolQueryBuilder);

        // 分页
        Integer page = searchRequest.getPage() - 1;// page 从0开始
        Integer size = searchRequest.getSize(); //把分页条件条件到构建器中
        queryBuilder.withPageable(PageRequest.of(page,size));

        //获取排序的条件
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if (StringUtils.isNotBlank(sortBy)){
            //把排序条件加给构建器
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

        // 品牌聚合查询
        String brandAggsName = "brands";
        // 类型聚合查询
        String categoryAggsName = "categories";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggsName).field("brandId"));
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggsName).field("cid3"));

        // 查询结果
        AggregatedPage<Goods> goodsResult = (AggregatedPage<Goods>)goodsRepository.search(queryBuilder.build());


        // 聚合结果
        List<Brand> brands = getBrandAgg(brandAggsName, goodsResult);
        List<Category> categories = getCategoryAgg(categoryAggsName, goodsResult);

        // 统计规格参数
        /*
            - 当分类结果为1行，统计规格参数
            - 根据分类查询当前分类的搜索的规格参数
            - 创建NativeQueryBuilder，使用上面搜索一样的条件
            - 循环上面可搜索规格参数，依次添加聚合
            - 处理结果k:参数名,options:聚合的结果数组
         */
        List<Map<String,Object>> specs = null;

        specs = getSpecs(categories.get(0).getId(),boolQueryBuilder);


        // 计算总页数
        long total = goodsResult.getTotalElements();
        long totalPages = goodsResult.getTotalPages();
        return new SearchResult(total,totalPages,goodsResult.getContent(), categories, brands, specs);

    }

    /**
     * 搜索过滤条件
     * @param searchRequest
     * @return
     */
    //这个方法用来构建查询条件以及过滤条件
    private BoolQueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));

        // 过滤条件
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        for(Map.Entry<String, String> entry : searchRequest.getFilter().entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            // 商品分类和品牌不用前后加修饰
            if (key != "cid3" && key != "brandId") {
                key = "specs." + key + ".keyword";
            }

            // 使用termQuery进行过滤
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));

        }
        return boolQueryBuilder.filter(filterQueryBuilder);
    }

    //规格参数的聚合应该和查询关联
    private List<Map<String, Object>> getSpecs(Long cid, QueryBuilder query) {
        List<Map<String, Object>> specs = new ArrayList<>();

        SpecParam sp = new SpecParam();
        sp.setCid(cid);
        sp.setSearching(true);
        List<SpecParam> specParams = this.specificationClient.selectSpecParamApi(sp);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //在做聚合之前先做查询，只有符合条件的规格参数才应该被查出来 queryBuilder.withQuery(query);
        for (SpecParam specParam : specParams) {
            String name = specParam.getName();//内存，产地
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs."+name +".keyword"));
        }
        AggregatedPage<Goods> aggs = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());
        Map<String, Aggregation> stringAggregationMap = aggs.getAggregations().asMap();
        for (SpecParam specParam : specParams) {
            Map<String,Object> spec = new HashMap<>();
            String name = specParam.getName();
            if (stringAggregationMap.get(name) instanceof StringTerms) {
                StringTerms stringTerms = (StringTerms) stringAggregationMap.get(name);
                List<String> val = new ArrayList<>();
                for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                    val.add(bucket.getKeyAsString());
                }
                spec.put("k",name);//内存，存储空间，屏幕尺寸
                spec.put("options",val);
                specs.add(spec);
            }
        }
        return specs;
    }




    /**
     * 品牌聚合数据处理
     * @param aggName
     * @param result
     * @return
     */
    private List<Brand> getBrandAgg(String aggName, AggregatedPage<Goods> result){
        LongTerms longTerms = (LongTerms) result.getAggregation(aggName);
        List<Long> brandIds = new ArrayList<>();
        for(LongTerms.Bucket bucket : longTerms.getBuckets()){
            brandIds.add(bucket.getKeyAsNumber().longValue());
        }
        return brandClient.selectBrandByIds(brandIds);
    }

    private List<Category> getCategoryAgg(String categoryAggsName, AggregatedPage<Goods> goodsResult) {
        LongTerms longTerms = (LongTerms) goodsResult.getAggregation(categoryAggsName);
        List<Long> categoryIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            categoryIds.add(bucket.getKeyAsNumber().longValue());
        }
        List<String> names = this.categoryClient.queryNameByIds(categoryIds);
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Category category = new Category();
            category.setId(categoryIds.get(i));
            //category.setName(names.get(i));
            category.setTitle(names.get(i));
            categories.add(category);
        }
        return categories;
    }

}
