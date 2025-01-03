package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.common.holder.LangHolder;
import com.channelblab.springrain.model.Multilingual;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 15:05
 * @description：
 * @modified By：
 */
public class MultilingualUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultilingualUtil.class);
    private static final Cache<String, List<Multilingual>> cache = Caffeine.newBuilder().build();

    /**
     * 获取目标语言对应的多语言数据
     *
     * @param symbol 标识
     * @return 对应的多语言值或标识
     */
    public static String getValue(String symbol) {
        String targetLang = LangHolder.getLang();
        List<Multilingual> multilingualList = cache.getIfPresent(targetLang);

        if (multilingualList == null || multilingualList.isEmpty()) {
            LOGGER.warn("多语言数据未初始化或未配置，语言为:{},多语言key为:{}", targetLang, symbol);
            return symbol;
        }

        return multilingualList.stream().filter(m -> m.getSymbol().equals(symbol)).map(Multilingual::getSymbolValue).findFirst().orElseGet(() -> {
            LOGGER.warn("未配置对应的多语言，语言为:{},多语言key为:{}", targetLang, symbol);
            return symbol;
        });
    }

    /**
     * 更新多语言数据
     *
     * @param multilingualList 多语言数据列表
     */
    public static void updateData(List<Multilingual> multilingualList) {
        Map<String, List<Multilingual>> groupedByLang = multilingualList.stream().collect(Collectors.groupingBy(Multilingual::getLangSymbol));
        groupedByLang.forEach(cache::put);
    }
}
