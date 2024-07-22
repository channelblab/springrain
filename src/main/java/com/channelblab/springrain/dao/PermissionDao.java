package com.channelblab.springrain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.channelblab.springrain.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-17 10:16
 * @description：
 * @modified By：
 */
@Repository
public interface PermissionDao extends BaseMapper<Permission> {
    List<Permission> selectAllPermissionByUserId(@Param("userId") String userId);
}
