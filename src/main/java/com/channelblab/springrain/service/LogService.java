package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.dao.LogDao;
import com.channelblab.springrain.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public IPage<Log> page(Integer page, Integer size,
                           LocalDateTime startDateTime,
                           LocalDateTime endDateTime) {

        IPage<Log> pageParam = new Page(page != null ? page : 1,
                size != null ? size : 10);
        LambdaQueryWrapper<Log> eq = Wrappers.lambdaQuery(Log.class)
                .eq(Log::getId, 1);


        return null;
    }
}
