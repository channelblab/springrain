package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.common.enums.PermissionType;
import com.channelblab.springrain.common.exception.BusinessException;
import com.channelblab.springrain.common.holder.UserHolder;
import com.channelblab.springrain.common.response.Response;
import com.channelblab.springrain.common.utils.PasswordUtil;
import com.channelblab.springrain.common.utils.UserUtil;
import com.channelblab.springrain.dao.PermissionDao;
import com.channelblab.springrain.dao.UserDao;
import com.channelblab.springrain.dao.UserRoleDao;
import com.channelblab.springrain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private UserRoleDao userRoleDao;


    public String loginByEmail(User user) {
        User userExist = userDao.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, user.getEmail()).eq(User::getEnable, true));
        if (userExist == null) {
            throw new BusinessException(Response.USERNAME_PASSWORD_ERROR_CODE, "username_password_error_code_msg");
        }
        if (PasswordUtil.match(user.getPass(), userExist.getPass())) {
            userExist.setPass(null);
            return UserUtil.genToken(userExist);
        } else {
            throw new BusinessException(Response.USERNAME_PASSWORD_ERROR_CODE, "username_password_error_code_msg");
        }

    }

    public List<User> currentOnlineUsers() {
        return UserUtil.onlineUsers();
    }

    public void kickOut(String userId) {
        UserUtil.kickOut(userId);
    }

    public IPage<User> page(Integer page, Integer size, String searchKey, String departmentId) {
        IPage<User> param = new Page<>(page, size);
        IPage<User> userIPage = userDao.selectCustomPage(param, departmentId, searchKey);
        List<User> records = userIPage.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<String> onlineUserIds = UserUtil.onlineUsers().stream().map(User::getId).collect(Collectors.toList());
            for (User record : records) {
                record.setPass(null);
                if (onlineUserIds.contains(record.getId())) {
                    record.setOnline(true);
                }

                List<Role> roles = userRoleDao.selectAllRolesByUserId(record.getId());
                record.setRoles(roles);

            }


        }
        return userIPage;
    }

    public UserInfoExt info() {
        UserInfoExt userInfoExt = new UserInfoExt();
        User user = userDao.selectById(UserHolder.getUser().getId());
        userInfoExt.setUser(user);
        List<Permission> permissions = permissionDao.selectAllPermissionByUserId(user.getId());

        //button permission
        List<String> permissionButtons = permissions.stream().filter(k -> k.getType().equals(PermissionType.BUTTON)).map(Permission::getSymbol).collect(Collectors.toList());
        userInfoExt.setButtonPermissions(permissionButtons);
        //menu permission
        List<Permission> menuPermissions = permissions.stream().filter(k -> k.getType().equals(PermissionType.MENU)).collect(Collectors.toList());
        userInfoExt.setMenuPermissions(menuPermissions);
        return userInfoExt;
    }

    public IPage<User> byDepartmentId(String departmentId, Integer page, Integer size) {
        IPage<User> param = new Page<>(page, size);
        return userDao.selectPage(param, Wrappers.lambdaQuery(User.class).eq(User::getEnable, true).eq(User::getDepartmentId, departmentId));
    }

    @Transactional
    public void addOrUpdate(User user) {
        User userExist = userDao.selectById(user.getId());
        if (userExist == null) {
            user.setEnable(true);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userDao.insert(user);
        } else {
            user.setUpdateTime(LocalDateTime.now());
            userDao.updateById(user);
        }

        userRoleDao.delete(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId, user.getId()));
        List<Role> roles = user.getRoles();
        roles.forEach(item -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(item.getId());
            userRoleDao.insert(ur);
        });
    }
}
