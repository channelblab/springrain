package com.channelblab.springrain;

import com.channelblab.springrain.common.utils.MultilingualUtil;
import com.channelblab.springrain.dao.MultilingualDao;
import com.channelblab.springrain.model.Multilingual;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

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

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        List<Multilingual> multilingualList = multilingualDao.selectList(null);
        MultilingualUtil.updateData(multilingualList);

    }
}
