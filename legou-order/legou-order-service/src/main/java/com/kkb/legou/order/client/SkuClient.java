package com.kkb.legou.order.client;

import com.kkb.legou.item.api.SkuApi;
import com.kkb.legou.item.po.Sku;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@FeignClient(name = "item-service", /*fallback = SkuClient.SkuClientFallback.class*/ fallbackFactory = SkuClient.SkuClientFallbackFactory.class)
public interface SkuClient extends SkuApi {

    @Component
    @RequestMapping("/sku-fallback")
    class SkuClientFallback implements SkuClient{
        private static final Logger LOGGER = LoggerFactory.getLogger(SkuClientFallback.class);

        @Override
        public List<Sku> selectSkusBySpuId(Long spuId) {
            LOGGER.info("异常发生, 进入fallback方法");
            return null;
        }

        @Override
        public Sku edit(Long id) {
            LOGGER.info("异常发生, 进入fallback方法");
            return null;
        }

        @Override
        public void decrCount(Integer num, Long skuId) {
            LOGGER.info("异常发生, 进入fallback方法");
        }

        @Override
        public void rollBackCount(Integer num, Long skuId) {
            LOGGER.info("异常发生, 进入fallback方法");
        }
    }

    @Component
    @RequestMapping("/sku-fallback-factory")
    class SkuClientFallbackFactory implements FallbackFactory<SkuClient> {

        Logger logger = LoggerFactory.getLogger(SkuClientFallbackFactory.class);

        @Override
        public SkuClient create(Throwable throwable) {
            throwable.printStackTrace();
            logger.error(throwable.getMessage());

            return new SkuClient() {
                @Override
                public List<Sku> selectSkusBySpuId(Long spuId) {
                    return null;
                }

                @Override
                public Sku edit(Long id) {
                    return null;
                }

                @Override
                public void decrCount(Integer num, Long skuId) {
                    logger.error("异常发生, 进入服务降级方法");

                }

                @Override
                public void rollBackCount(Integer num, Long skuId) {
                    logger.error("异常发生, 进入服务降级方法");
                }
            };

        }



    }

}
