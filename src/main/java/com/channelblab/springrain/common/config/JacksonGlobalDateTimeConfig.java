package com.channelblab.springrain.common.config;

import com.channelblab.springrain.common.holder.ZoneIdHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 国际化时间全局配置jackson
 *
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2025-12-29 11:24
 * @description：
 * @modified By：
 */
@Configuration
public class JacksonGlobalDateTimeConfig {
    private static final Pattern TIME_FIELD_PATTERN = Pattern.compile(".*(?i)(time|date|At)$");
    @Value("${spring.jackson.dateFormat:}")
    private String dateFormatString;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.modulesToInstall(new SimpleModule() {{
            setSerializerModifier(new BeanSerializerModifier() {
                @Override
                public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                    for (BeanPropertyWriter writer : beanProperties) {
                        String fieldName = writer.getName();
                        JavaType type = writer.getType();
                        if (TIME_FIELD_PATTERN.matcher(fieldName).matches() && type.isTypeOrSubTypeOf(Long.class)) {
                            JsonSerializer<?> existing = writer.getSerializer();
                            if (existing == null) {
                                writer.assignSerializer(new JsonSerializer<Object>() {
                                    @Override
                                    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                                        if (value == null) {
                                            gen.writeNull();
                                            return;
                                        }
                                        if (value instanceof Long) {
                                            Long dateTime = (Long) value;
                                            if (dateTime <= 0) {
                                                gen.writeNull();
                                                return;
                                            }
                                            JsonFormat annotationInField = writer.getAnnotation(JsonFormat.class);
                                            if (annotationInField != null && !ObjectUtils.isEmpty(annotationInField.pattern())) {
                                                dateFormatString = annotationInField.pattern();
                                            } else {
                                                if (ObjectUtils.isEmpty(dateFormatString)) {
                                                    dateFormatString = "yyyy-MM-dd HH:mm:ss";
                                                }
                                            }


                                            ZoneId zoneId = ZoneIdHolder.getZone();
                                            //格式化由jackson的配置传入，由全局配置或者字段上的注解pattern
                                            String formatted = Instant.ofEpochSecond(dateTime).atZone(zoneId).format(DateTimeFormatter.ofPattern(dateFormatString));
                                            gen.writeString(formatted);
                                        }
                                    }
                                });
                            }
                        }
                    }

                    return beanProperties;
                }
            });
        }});
    }
}