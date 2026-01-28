package com.channelblab.springrain.common.config;

import com.channelblab.springrain.common.enums.HeaderName;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

/**
 * @author ：dengyi
 * @date ：Created in 2022/2/16 14:26
 * @description：swagger接口文档配置
 * @modified By：
 */
@Configuration
public class SwaggerConfig implements OperationCustomizer {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("API Doc/API文档").version("1.0.0")).components(
                        new Components().addSecuritySchemes("mySecretHeader", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(HeaderName.HEADER_TOKEN.getValue())))
                .addSecurityItem(new SecurityRequirement().addList("mySecretHeader"));
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 添加 lang Header
        operation.addParametersItem(new Parameter().in("header").name(HeaderName.HEADER_LANG.getValue()).description("language/语言").example("zh-CN").required(false).schema(new StringSchema()));
        operation.addParametersItem(
                new Parameter().in("header").name(HeaderName.HEADER_TIME_ZONE.getValue()).description("time-zone/时区").example("Asia/Shanghai").required(false).schema(new StringSchema()));
        return operation;
    }
}