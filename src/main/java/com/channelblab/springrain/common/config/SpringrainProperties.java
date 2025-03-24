package com.channelblab.springrain.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-08-28 15:34
 * @description：
 * @modified By：
 */
@Component
@ConfigurationProperties(prefix = "springrain")
public class SpringrainProperties {
    private DbType dbType;
    private String newUserPass = "abc123";
    private List<String> excludeUris;
}
