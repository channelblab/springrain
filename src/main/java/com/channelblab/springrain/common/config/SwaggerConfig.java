package com.channelblab.springrain.common.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.Constants.ALL_PATTERN;

/**
 * @author ：dengyi
 * @date ：Created in 2022/2/16 14:26
 * @description：swagger接口文档配置
 * @modified By：
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder().group("Actuator").pathsToMatch(ALL_PATTERN).addOpenApiCustomiser(openApi -> openApi.info(new Info().title("API文档").version("1.0.0"))).build();
    }
}
