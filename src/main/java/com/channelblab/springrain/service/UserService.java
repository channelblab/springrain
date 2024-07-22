package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.PasswordUtil;
import com.channelblab.springrain.common.utils.UserUtil;
import com.channelblab.springrain.dao.UserDao;
import com.channelblab.springrain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-22 10:08
 * @description：
 * @modified By：
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;


    public String loginByEmail(User user) {
        User userExist = userDao.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, user.getEmail()));
        if (userExist == null) {
            throw new BusinessException(Response.USERNAME_PASSWORD_ERROR_CODE, "user_name_password_error");
        }
        if (PasswordUtil.match(user.getPass(), userExist.getPass())) {
            userExist.setPass(null);
            return UserUtil.genToken(userExist);
        } else {
            throw new BusinessException(Response.USERNAME_PASSWORD_ERROR_CODE, "user_name_password_error");
        }

    }

    public List<User> currentOnlineUsers() {
        return UserUtil.onlineUsers();
    }

    public void kickOut(String userId) {
        UserUtil.kickOut(userId);
    }

    public IPage<User> page(Integer page, Integer size, String userId, String name) {
        IPage<User> param = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(!ObjectUtils.isEmpty(userId), User::getId, userId).like(!ObjectUtils.isEmpty(name), User::getName, name);
        return userDao.selectPage(param, wrapper);
    }
}
