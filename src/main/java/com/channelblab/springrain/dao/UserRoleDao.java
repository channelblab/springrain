package com.channelblab.springrain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.channelblab.springrain.model.Role;
import com.channelblab.springrain.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-22 9:16
 * @description：
 * @modified By：
 */
@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {
    List<Role> selectAllRolesByUserId(@Param("id") String id);
}
