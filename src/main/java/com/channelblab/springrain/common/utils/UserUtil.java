package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.model.User;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 用户工具类
 *
 * @author dengyi (email:dengyi@dengyi.pro)
 * @date 2022-01-23
 */
@Slf4j
public class UserUtil {
    //过期时间1小时
    private static final Integer LOGIN_EXPIRE_SECONDS = 60 * 60;
    static Cache<Object, Object> cache;

    static {
        cache = Caffeine.newBuilder().expireAfterAccess(Duration.ofSeconds(LOGIN_EXPIRE_SECONDS)).build();
    }

    private static Cache<Object, Object> getCaffeine() {
        return cache;
    }

    public static String genToken(User user) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        getCaffeine().put(uuid, user);
        return uuid;
    }

    /**
     * 解析token
     *
     * @param token 用户token
     * @return
     */
    public static User decToken(String token) {
        Object ifPresent = getCaffeine().getIfPresent(token);
        if (ifPresent == null) {
            //未登录或者登录过期
            throw new BusinessException(Response.LOGIN_EXPIRE_CODE, "login_expire");
        }
        return (User) ifPresent;
    }

    public static void kickOut(String userId) {
        Cache<Object, Object> caffeine = getCaffeine();
        for (Object key : caffeine.asMap().keySet()) {
            User u = (User) caffeine.getIfPresent(key);
            if (u.equals(userId)) {
                caffeine.invalidate(key);
            }
        }
    }

    public static List<User> onlineUsers() {
        List<User> onlineUsers = new ArrayList<>();
        Cache<Object, Object> caffeine = getCaffeine();
        for (Object key : caffeine.asMap().keySet()) {
            User u = (User) caffeine.getIfPresent(key);
            onlineUsers.add(u);
        }
        return onlineUsers;
    }

}
