package com.channelblab.springrain;

import com.channelblab.springrain.dao.MultilingualDao;
import com.channelblab.springrain.model.Multilingual;
import com.github.benmanes.caffeine.cache.Cache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 10:58
 * @description：
 * @modified By：
 */
@Configuration
@MapperScan(basePackages = "com.channelblab.springrain.dao")
@ComponentScan(basePackages = "com.channelblab.springrain")
public class SpringRainApplication {

    @Autowired
    private MultilingualDao multilingualDao;
    @Autowired
    private Cache multilingualCache;


    @PostConstruct
    public void init() {
        System.err.println("-------------------");

        List<Multilingual> multilinguals = multilingualDao.selectList(null);

        multilingualCache.put("zh", multilinguals);
    }
}
