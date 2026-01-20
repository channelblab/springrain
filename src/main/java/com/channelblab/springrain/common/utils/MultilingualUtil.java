package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.common.enums.CachePrefix;
import com.channelblab.springrain.common.enums.MultilingualType;
import com.channelblab.springrain.common.holder.LangHolder;
import com.channelblab.springrain.model.Multilingual;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 多语言工具类
 *
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 15:05
 * @description：
 * @modified By：
 */
@Slf4j
public class MultilingualUtil {
    private static volatile CacheManager cacheManager;

    public static void init(CacheManager cm) {
        if (cacheManager == null) {
            cacheManager = cm;
        }
    }

    /**
     * 获取目标语言对应的多语言数据
     *
     * @param symbol 标识
     * @return 对应的多语言值或标识
     */
    public static String get(String symbol) {
        String targetLang = LangHolder.getLang();
        if (!ObjectUtils.isEmpty(symbol) && !ObjectUtils.isEmpty(targetLang)) {
            List list = cacheManager.getCache(CachePrefix.MULTILINGUAL.getValue()).get(targetLang, List.class);
            if (!CollectionUtils.isEmpty(list)) {
                for (Object item : list) {
                    Multilingual multilingual = (Multilingual) item;
                    if (multilingual.equals(symbol)) {
                        return multilingual.getSymbolValue();
                    }
                }
            }
        }
        log.warn("not config multilingual/未配置多语言信息,lang is/语言为:{},key is/多语言key为:{}", targetLang, symbol);
        return symbol;
    }

    /**
     * 更新多语言数据
     *
     * @param multilingualList 多语言数据列表
     */
    public static void update(List<Multilingual> multilingualList) {
        Cache cache = cacheManager.getCache(CachePrefix.MULTILINGUAL.getValue());

        ArrayList langList = multilingualList.stream().filter(item -> item.getType().equals(MultilingualType.FRONTEND))
                .collect(Collectors.collectingAndThen(Collectors.toMap(Multilingual::getLang, Function.identity(), (existing, replacement) -> existing), map -> new ArrayList(map.values())));
        cache.put("langList", langList);
        Map<String, List<Multilingual>> groupedByLang = multilingualList.stream().collect(Collectors.groupingBy(Multilingual::getLang));
        groupedByLang.forEach((key, value) -> {
            //frontend
            List<Multilingual> frontendList = value.stream().filter(item -> item.getType().equals(MultilingualType.FRONTEND)).collect(Collectors.toList());
            //backend
            List<Multilingual> backendList = value.stream().filter(item -> item.getType().equals(MultilingualType.BACKEND)).collect(Collectors.toList());

            cache.put(key + "::" + MultilingualType.FRONTEND, frontendList);
            cache.put(key + "::" + MultilingualType.BACKEND, backendList);
        });
    }
}