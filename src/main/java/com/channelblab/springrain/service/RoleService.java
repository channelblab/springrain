package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.dao.RoleDao;
import com.channelblab.springrain.dao.RolePermissionDao;
import com.channelblab.springrain.dao.UserRoleDao;
import com.channelblab.springrain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private UserRoleDao userRoleDao;


    public void addOrUpdate(Role role) {
        if (ObjectUtils.isEmpty(role.getId())) {
            //add
            role.setType("CUSTOM");
            role.setCreateTime(new Date());
            role.setUpdateTime(new Date());
            roleDao.insert(role);
        } else {
            //update
            role.setUpdateTime(new Date());
            roleDao.updateById(role);
        }
        //fully update
        rolePermissionDao.delete(Wrappers.lambdaQuery(RolePermission.class).eq(RolePermission::getRoleId, role.getId()));
        List<Permission> permissions = role.getPermissions();
        permissions.forEach(item -> {
            RolePermission rp = new RolePermission();
            rp.setRoleId(role.getId());
            rp.setPermissionId(item.getId());
            rolePermissionDao.insert(rp);
        });

        userRoleDao.delete(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getRoleId, role.getId()));
        List<User> users = role.getUsers();
        users.forEach(item -> {
            UserRole ur = new UserRole();
            ur.setUserId(item.getId());
            ur.setRoleId(role.getId());
            userRoleDao.insert(ur);
        });
    }

    public IPage<Role> page(Integer page, Integer size, String name) {
        IPage<Role> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery(Role.class).like(!ObjectUtils.isEmpty(name), Role::getName, name);
        IPage<Role> roleIPage = roleDao.selectPage(pageParam, queryWrapper);
        List<Role> records = roleIPage.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(item -> {
                List<RolePermission> rolePermissions = rolePermissionDao.selectList(Wrappers.lambdaQuery(RolePermission.class).eq(RolePermission::getRoleId, item.getId()));
                List<Permission> permissions = new ArrayList<>();
                for (RolePermission rolePermission : rolePermissions) {
                    Permission permission = new Permission();
                    permission.setId(rolePermission.getPermissionId());
                    permissions.add(permission);
                }
                item.setPermissions(permissions);
                List<UserRole> userRoles = userRoleDao.selectList(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getRoleId, item.getId()));
                List<User> users = new ArrayList<>();
                for (UserRole userRole : userRoles) {
                    User user = new User();
                    user.setId(userRole.getUserId());
                    users.add(user);
                }
                item.setUsers(users);
            });
        }
        return roleIPage;
    }

    @Transactional
    public void delete(String id) {
        Role role = roleDao.selectById(id);
        if (role == null) {
            return;
        }
        if ("SYSTEM".equals(role.getType())) {
            throw new BusinessException("system.role.can.not.delete");
        }
        roleDao.deleteById(id);
        rolePermissionDao.delete(Wrappers.lambdaQuery(RolePermission.class).eq(RolePermission::getRoleId, id));
        userRoleDao.delete(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getRoleId, id));

    }
}
