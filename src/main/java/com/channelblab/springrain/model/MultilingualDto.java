package com.channelblab.springrain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:16
 * @description：
 * @modified By：
 */
@Data
@Schema
public class MultilingualDto {
    private List<Multilingual> standardLang;
    private Map<String, List<Multilingual>> totalLangData;
}
