package com.channelblab.springrain.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：dengyi
 * @date ：Created in 2022/2/24 9:57
 * @description：mybatis-plus配置
 * @modified By：
 */
@Configuration
public class MybatisPlusConfig {

  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 分页插件
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    // 乐观锁插件
    // interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    return interceptor;
  }
}
