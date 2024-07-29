package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.channelblab.springrain.dao.DepartmentDao;
import com.channelblab.springrain.dao.UserDao;
import com.channelblab.springrain.model.Department;
import com.channelblab.springrain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-26 13:30
 * @description：
 * @modified By：
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;

    public List<Department> tree() {
        List<Department> departments = departmentDao.selectList(Wrappers.lambdaQuery(Department.class).eq(Department::getEnable, true));
        List<Department> firstDepartments = departments.stream().filter(k -> k.getParentId() == null).collect(Collectors.toList());
        iterSubDepartments(firstDepartments, departments);
        return firstDepartments;
    }

    private void iterSubDepartments(List<Department> firstDepartments, List<Department> departments) {
        for (Department firstDepartment : firstDepartments) {
            List<Department> subDepartments = departments.stream().filter(k -> firstDepartment.getId().equals(k.getParentId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(subDepartments)) {
                firstDepartment.setChildren(subDepartments);
                iterSubDepartments(subDepartments, departments);
            }
        }
    }

    @Transactional
    public void delete(String departmentId) {
        Department department = departmentDao.selectById(departmentId);
        department.setEnable(false);
        departmentDao.updateById(department);

        List<User> users = userDao.selectList(Wrappers.lambdaQuery(User.class).eq(User::getEnable, true).eq(User::getDepartmentId, departmentId));

        users.forEach(user->{
            user.setDepartmentId(null);
            userDao.updateById(user);
        });
    }
}
