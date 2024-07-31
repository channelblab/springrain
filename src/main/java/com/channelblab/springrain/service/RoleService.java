package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.dao.RoleDao;
import com.channelblab.springrain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-23 9:04
 * @description：
 * @modified By：
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;


    public void add(Role role) {

    }

    public IPage<Role> page(Integer page, Integer size, String name) {
        IPage<Role> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery(Role.class).like(!ObjectUtils.isEmpty(name), Role::getName, name);
        return roleDao.selectPage(pageParam, queryWrapper);
    }
}
