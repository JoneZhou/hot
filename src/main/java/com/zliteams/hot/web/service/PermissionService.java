package com.zliteams.hot.web.service;

import java.util.List;
import java.util.Set;

import com.zliteams.hot.core.generic.GenericService;
import com.zliteams.hot.web.model.Permission;
import com.zliteams.hot.web.model.Role;

/**
 * 权限 业务接口
 * 
 * @author StarZou
 * @since 2014年6月10日 下午12:02:39
 **/
public interface PermissionService extends GenericService<Permission, Long> {

    /**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionsByRoleId(Set<Role> roles);

	Set<String> findPermissions(Long id);

}
