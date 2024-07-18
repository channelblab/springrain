package com.channelblab.springrain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：dengyi
 * @date ：Created in 2022/2/16 14:26
 * @description：swagger接口文档配置
 * @modified By：
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
        List<RequestParameter> list = new ArrayList<>();
        list.add(
                new RequestParameterBuilder()
                        .name("token")
                        .description("token")
                        .in(ParameterType.HEADER)
                        .build());
        list.add(
                new RequestParameterBuilder()
                        .name("factoryId")
                        .description("factoryId")
                        .in(ParameterType.HEADER)
                        .build());
        list.add(
                new RequestParameterBuilder()
                        .name("lang")
                        .description("language")
                        .in(ParameterType.HEADER)
                        .build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalRequestParameters(list)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("考勤结薪系统API文档")
                .description("考勤结薪系统API文档")
                .contact(new Contact("邓艺", "", "yi.deng@minthgroup.com"))
                .version("1.0.0")
                .build();
    }
}
