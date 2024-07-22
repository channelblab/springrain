package com.channelblab.springrain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.channelblab.springrain.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-22 10:09
 * @description：
 * @modified By：
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
