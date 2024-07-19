package com.channelblab.springrain.common.holder;


import com.channelblab.springrain.model.User;

/**
 * @author DengYi
 * @version v1.0
 */
public class UserHolder {

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    /**
     * 获取用户完整信息 注意：暂时不用，项目中不需要
     *
     * @return 用户信息实体
     */
    public static User getUser() {
        return LOCAL.get();
    }

    /**
     * 设置用户信息
     *
     * @param user 用户信息
     */
    public static void setUser(User user) {
        LOCAL.set(user);
    }

    /**
     * 清空
     */
    public static void remove() {
        LOCAL.remove();
    }
}
