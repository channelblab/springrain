package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

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


    public IPage<Log> page(String userId, Integer page, Integer size, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        IPage<Log> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Log> qr = Wrappers.lambdaQuery(Log.class).eq(!ObjectUtils.isEmpty(userId), Log::getUserId, userId).ge(startDateTime != null, Log::getCreateTime, startDateTime)
                .le(endDateTime != null, Log::getCreateTime, endDateTime).orderByDesc(Log::getCreateTime);
        return logDao.selectPage(pageParam, qr);
    }
}
