package com.zliteams.hot.web.service;

import java.util.List;
import java.util.Set;

import com.zliteams.hot.core.generic.GenericService;
import com.zliteams.hot.web.model.Role;

/**
 * 角色 业务接口
 * 
 * @author StarZou
 * @since 2014年6月10日 下午4:15:01
 **/
public interface RoleService extends GenericService<Role, Long> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

	// 添加角色-权限之间关系
	public void correlationPermissions(Long roleId, Long... permissionIds);

	// 移除角色-权限之间关系
	public void uncorrelationPermissions(Long roleId, Long... permissionIds);

	Set<String> findRoles(Long userId);
}
