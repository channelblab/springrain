package com.channelblab.springrain.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web基础配置
 *
 * <p>主要配置跨域处理和进行登录拦截器配置
 *
 * @author dengyi
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowCredentials(true).maxAge(3600).allowedHeaders("*");
    }

}
