package com.channelblab.springrain;

import com.channelblab.springrain.common.utils.MultilingualUtil;
import com.channelblab.springrain.common.utils.UserUtil;
import com.channelblab.springrain.dao.MultilingualDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.metrics.ApplicationStartup;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 10:58
 * @description：
 * @modified By：
 */
@EnableCaching
@Configuration
@MapperScan(basePackages = "com.channelblab.springrain.dao")
@ComponentScan(basePackages = "com.channelblab.springrain")
public class SpringRainApplication {

    @Autowired
    private MultilingualDao multilingualDao;
    @Autowired
    private CacheManager cacheManager;

    //todo we load every  frequently use things into system cache,and we need to reload it when things change by apis
    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        //        List<Multilingual> multilingualList = multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class));
        //        MultilingualUtil.update(multilingualList);

        //initial user util
        MultilingualUtil.init(cacheManager);
        UserUtil.init(cacheManager);

    }

    @EventListener(ApplicationStartup.class)
    public void shutdownBanner() {
        System.setProperty("mybatis-plus.global-config.banner", "false");

    }


}