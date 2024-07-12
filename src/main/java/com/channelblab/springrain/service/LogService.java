package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

        return null;
    }
}
