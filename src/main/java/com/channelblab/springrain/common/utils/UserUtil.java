package com.channelblab.springrain.common.utils;

import com.channelblab.springrain.common.enums.CachePrefix;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;


/**
 * 用户工具类
 *
 * @author dengyi (email:dengyi@dengyi.pro)
 * @date 2022-01-23
 */
@Slf4j
public class UserUtil {
    // cache
    private static volatile CacheManager cacheManager;

    public static void init(CacheManager cm) {
        if (cacheManager == null) {
            cacheManager = cm;
        }
    }

    public static String genToken(User user) {
        kickOut(user.getId());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        List list = cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).get(CachePrefix.LOGIN_USER.getValue(), List.class);
        HashMap<Object, Object> param = new HashMap<>();
        param.put("dateTime", LocalDateTime.now());
        param.put("token", uuid);
        param.put("user", user);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(param);
        cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).put(CachePrefix.LOGIN_USER.getValue(), list);
        return uuid;
    }

    /**
     * 解析token
     *
     * @param token 用户token
     * @return
     */
    public static User decToken(String token) {
        //blank or null validate
        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(Response.LOGIN_EXPIRE_CODE, "login_expire");
        }
        //todo 可以增加其他方式优先返回
        List list = cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).get(CachePrefix.LOGIN_USER.getValue(), List.class);
        if (list == null) {
            //虚假token
            throw new BusinessException(Response.LOGIN_EXPIRE_CODE, "login_expire");
        }
        User u = null;
        for (Object item : list) {
            Map<String, Object> resMap = (Map<String, Object>) item;
            if (resMap.get("token").equals(token)) {
                u = (User) resMap.get("user");
            }
        }
        return u;
    }

    public static void kickOut(String userId) {
        if (ObjectUtils.isEmpty(userId)) {
            return;
        }

        List list = cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).get(CachePrefix.LOGIN_USER.getValue(), List.class);
        if (list == null) {
            return;
        }
        list.removeIf(item -> {
            Map<String, Object> resMap = (Map<String, Object>) item;
            return ((User) resMap.get("user")).getId().equals(userId);
        });
        cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).put(CachePrefix.LOGIN_USER.getValue(), list);
    }

    public static List<User> onlineUsers() {
        List list = cacheManager.getCache(CachePrefix.LOGIN_USER.getValue()).get(CachePrefix.LOGIN_USER.getValue(), List.class);
        List<User> resList = new ArrayList<>();
        list.forEach(item -> {
            resList.add((User) ((Map<String, Object>) item).get("user"));
        });
        return resList;
    }

}