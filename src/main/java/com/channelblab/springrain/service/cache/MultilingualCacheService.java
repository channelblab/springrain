package com.channelblab.springrain.service.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.channelblab.springrain.dao.MultilingualDao;
import com.channelblab.springrain.model.Multilingual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2026-01-08 10:00
 * @description：
 * @modified By：
 */
@Component
public class MultilingualCacheService {
    @Autowired
    private MultilingualDao multilingualDao;

    @Cacheable(value = "multilingual", key = "#local +'::'+ #type")
    public List<Multilingual> getMultilingualByLocal(String local, String type) {
        return multilingualDao.selectList(new LambdaQueryWrapper<Multilingual>().eq(Multilingual::getLang, local).eq(!ObjectUtils.isEmpty(type), Multilingual::getType, type));
    }

    @Cacheable(value = "multilingual", key = "'langList'")
    public List<Multilingual> langList() {
        return multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class).groupBy(Multilingual::getLang, Multilingual::getLangDescribe));
    }

}