package com.zliteams.hot.web.dao;

import java.util.List;
import java.util.Set;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.web.model.Permission;
import com.zliteams.hot.web.model.Role;

/**
 * 权限 Dao 接口
 **/
public interface PermissionMapper extends GenericDao<Permission, Long> {

    /**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionsByRoleId(Set<Role> roles);

	Set<String> findPermissions(Long userId);
}