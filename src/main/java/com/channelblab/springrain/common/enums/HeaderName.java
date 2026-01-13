package com.channelblab.springrain.common.enums;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2026-01-13 10:02
 * @description：
 * @modified By：
 */
public enum HeaderName {
    HEADER_TOKEN("Token"), HEADER_LANG("Lang"), HEADER_TIME_ZONE("Time-Zone");

    private String value;

    HeaderName(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
}