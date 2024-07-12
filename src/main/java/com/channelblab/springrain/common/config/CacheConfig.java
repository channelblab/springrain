package com.channelblab.springrain.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 14:51
 * @description：
 * @modified By：
 */
@Configuration
public class CacheConfig {

    @Bean("multilingualCache")
    public Cache<String, Object> multilingualCache() {
        return Caffeine.newBuilder().build();
    }
}
