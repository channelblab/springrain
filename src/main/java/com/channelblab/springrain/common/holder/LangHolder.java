package com.channelblab.springrain.common.holder;


/**
 * @author DengYi
 * @version v1.0
 */
public class LangHolder {

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();


    public static String getLang() {
        return LOCAL.get();
    }

    public static void setLang(String lang) {
        LOCAL.set(lang);
    }

    /**
     * 清空
     */
    public static void remove() {
        LOCAL.remove();
    }
}
