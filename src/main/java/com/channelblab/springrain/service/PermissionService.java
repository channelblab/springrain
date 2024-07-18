package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.common.utils.MultilingualUtil;
import com.channelblab.springrain.dao.PermissionDao;
import com.channelblab.springrain.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-17 10:14
 * @description：
 * @modified By：
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public IPage<Permission> page(Integer page, Integer size) {
        IPage<Permission> pageParam = new Page<>(page == null ? 1 : page,
                size == null ? 10 : size);
        return permissionDao.selectPage(pageParam, null);
    }

    public List<Permission> tree() {
        List<Permission> allPermissions = permissionDao.selectList(null);
        for (Permission allPermission : allPermissions) {
            allPermission.setName(
                    MultilingualUtil.getValue(allPermission.getName()));
        }


        List<Permission> firstPermissions = allPermissions.stream()
                .filter(k -> k.getParentId() == null)
                .collect(Collectors.toList());

        List<Permission> subPermissions = allPermissions.stream()
                .filter(k -> k.getParentId() != null)
                .collect(Collectors.toList());
        for (Permission firstPermission : firstPermissions) {
            iterSub(firstPermission, subPermissions);
        }
        return firstPermissions;
    }

    private void iterSub(Permission firstPermission,
                         List<Permission> allPermissions) {
        List<Permission> child = allPermissions.stream()
                .filter(k -> k.getParentId().equals(firstPermission.getId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            firstPermission.setChildren(child);
            for (Permission permission : child) {
                iterSub(permission, allPermissions);
            }

        }

    }
}
