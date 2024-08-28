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
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
        List<Department> departments = departmentDao.selectList(Wrappers.lambdaQuery(Department.class).eq(Department::getEnable, true));
        List<Department> departmentList = departments.stream().filter(dept -> dept.getId().equals(departmentId)).collect(Collectors.toList());
        if (!departmentList.isEmpty()) {
            Department department = departmentList.get(0);
            List<Department> allDepts = new ArrayList<>();
            allDepts.add(department);
            iterAllSubDept(allDepts, department, departments);
            for (Department dept : allDepts) {
                dept.setEnable(false);
                departmentDao.updateById(dept);
            }
            List<String> idList = allDepts.stream().map(Department::getId).collect(Collectors.toList());
            List<User> users = userDao.selectList(Wrappers.lambdaQuery(User.class).eq(User::getEnable, true).in(User::getDepartmentId, idList));
            users.forEach(user -> {
                user.setDepartmentId(null);
                userDao.updateById(user);
            });
        }
    }

    private void iterAllSubDept(List<Department> allDepts, Department department, List<Department> departments) {
        List<Department> collect = departments.stream().filter(dept -> department.getId().equals(dept.getParentId())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            allDepts.addAll(collect);
            for (Department subDept : collect) {
                iterAllSubDept(allDepts, subDept, departments);
            }
        }
    }

    public List<Department> deptUserTree() {
        List<Department> departments = departmentDao.selectList(Wrappers.lambdaQuery(Department.class).eq(Department::getEnable, true));
        List<Department> rootDepartments = departments.stream().filter(department -> department.getParentId() == null).collect(Collectors.toList());
        List<User> users = userDao.selectList(Wrappers.lambdaQuery(User.class).eq(User::getEnable, true));
        List<User> usersInRoot = users.stream().filter(user -> user.getDepartmentId() == null).collect(Collectors.toList());
        if (rootDepartments.isEmpty()) {
            return new ArrayList<>();
        }
        for (Department rootDepartment : rootDepartments) {
            iterAllDepartmentAndUsers(rootDepartment, users, departments);
        }
        if (!usersInRoot.isEmpty()) {
            //未分配的放在第一个根下
            Department firstRoot = rootDepartments.get(0);
            List<Department> depts = new ArrayList<>();
            for (User user : usersInRoot) {
                Department userAsDept = new Department();
                userAsDept.setId(user.getId());
                userAsDept.setName(user.getName());
                depts.add(userAsDept);
            }
            firstRoot.getChildren().addAll(depts);
        }
        return rootDepartments;
    }

    private void iterAllDepartmentAndUsers(Department rootDepartment, List<User> users, List<Department> allDepartments) {
        List<User> usersInDept = users.stream().filter(user -> rootDepartment.getId().equals(user.getDepartmentId())).collect(Collectors.toList());
        if (!usersInDept.isEmpty()) {
            List<Department> departments = new ArrayList<>();
            for (User u : usersInDept) {
                Department userAsDept = new Department();
                userAsDept.setId(u.getId());
                userAsDept.setName(u.getName());
                departments.add(userAsDept);
            }
            rootDepartment.setChildren(departments);
        }
        List<Department> children = allDepartments.stream().filter(dept -> rootDepartment.getId().equals(dept.getParentId())).collect(Collectors.toList());
        if (!children.isEmpty()) {
            if (rootDepartment.getChildren() != null) {
                rootDepartment.getChildren().addAll(children);
            } else {
                rootDepartment.setChildren(children);
            }
            for (Department child : children) {
                iterAllDepartmentAndUsers(child, users, allDepartments);
            }

        }
    }


    @Transactional
    public void addOrUpdate(Department department) {
        if (ObjectUtils.isEmpty(department.getId())) {
            department.setEnable(true);
            departmentDao.insert(department);
        } else {
            departmentDao.updateById(department);
        }

    }

    public List<Department> search(String key) {

        return departmentDao.selectList(Wrappers.lambdaQuery(Department.class).like(Department::getName, key).or().eq(Department::getId, key));
    }
}
