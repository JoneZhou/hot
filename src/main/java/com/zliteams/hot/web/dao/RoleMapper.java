package com.zliteams.hot.web.dao;

import java.util.List;
import java.util.Set;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.web.model.Role;

/**
 * 角色Dao 接口
 * 
 **/
public interface RoleMapper extends GenericDao<Role, Long> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param id
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

	Set<String> findRoles(Long userId);
}