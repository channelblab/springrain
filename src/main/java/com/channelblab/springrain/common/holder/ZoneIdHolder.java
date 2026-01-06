package com.channelblab.springrain.common.holder;

import org.springframework.util.ObjectUtils;

import java.time.ZoneId;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2025-12-29 10:58
 * @description：
 * @modified By：
 */
public class ZoneIdHolder {
    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    public static void setZone(String zone) {
        LOCAL.set(zone);
    }

    public static ZoneId getZone() {
        String zoneString = LOCAL.get();
        return ObjectUtils.isEmpty(zoneString) ? ZoneId.systemDefault() : ZoneId.of(zoneString);
    }

    public static void clear() {
        LOCAL.remove();
    }
}