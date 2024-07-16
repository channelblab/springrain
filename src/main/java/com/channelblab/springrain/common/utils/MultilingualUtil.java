package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.model.Multilingual;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 15:05
 * @description：
 * @modified By：
 */
public class MultilingualUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MultilingualUtil.class);

    private static Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().build();
    }

    public static String getValue(String symbol) {
        String language = LocaleContextHolder.getLocale()
                .getLanguage() + "_" + LocaleContextHolder.getLocale()
                .getCountry();

        List<Multilingual> multilingualList = (List<Multilingual>) cache.getIfPresent(
                language);
        if (multilingualList == null) {
            LOGGER.warn("多语言数据未初始化，多语言key为:{}", symbol);
            return symbol;
        }
        if (multilingualList.isEmpty()) {
            LOGGER.warn("多语言数据为空，多语言key为:{}", symbol);
            return symbol;
        }
        //查找对应的value
        Optional<String> firstValue = multilingualList.stream()
                .filter(m -> m.getSymbol().equals(symbol))
                .map(Multilingual::getValue).findFirst();
        if (firstValue.isPresent()) {
            return firstValue.get();
        } else {
            LOGGER.warn("未配置对应的多语言，多语言key为:{}", symbol);
            return symbol;
        }


    }

    public static void updateData(List<Multilingual> multilingualList) {

        Map<String, List<Multilingual>> groupedByLang = multilingualList.stream()
                .collect(Collectors.groupingBy(Multilingual::getLang));
        groupedByLang.forEach((lang, list) -> {
            cache.put(lang, list);
        });


    }
}
