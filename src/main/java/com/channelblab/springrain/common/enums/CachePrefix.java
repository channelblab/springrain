package com.channelblab.springrain.common.enums;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2026-01-12 10:00
 * @description：
 * @modified By：
 */
public enum CachePrefix {
    LOGIN_USER("loginUser"), USER_PERMISSION("userPermission"), MULTILINGUAL("multilingual");
    private String value;

    CachePrefix(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
}