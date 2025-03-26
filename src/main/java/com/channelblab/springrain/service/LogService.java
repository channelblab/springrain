package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.dao.UserDao;
import com.channelblab.springrain.model.Log;
import com.channelblab.springrain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 11:20
 * @description：
 * @modified By：
 */
@Service
public class LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private UserDao userDao;


    public IPage<Log> page(String userId, Integer page, Integer size, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        IPage<Log> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Log> qr = Wrappers.lambdaQuery(Log.class).eq(!ObjectUtils.isEmpty(userId), Log::getCreateUserId, userId).ge(startDateTime != null, Log::getCreateDateTime, startDateTime)
                .le(endDateTime != null, Log::getCreateDateTime, endDateTime).orderByDesc(Log::getCreateDateTime);
        IPage<Log> logIPage = logDao.selectPage(pageParam, qr);
        List<Log> records = logIPage.getRecords();
        //why I do this? because I dont want to write SQL and I want to improve the performance
        if (!CollectionUtils.isEmpty(records)) {
            List<String> userIdList = records.stream().filter(k -> !ObjectUtils.isEmpty(k.getCreateUserId())).map(Log::getCreateUserId).collect(Collectors.toList());
            // here is the key to improve the performance,select all users use one SQL
            List<User> users = userDao.selectList(Wrappers.lambdaQuery(User.class).in(User::getId, userIdList));
            records.forEach(record -> users.forEach(user -> {
                if (user.getId().equals(record.getCreateUserId())) {
                    user.setPass(null);
                    record.setUser(user);
                }
            }));

        }
        return logIPage;
    }
}