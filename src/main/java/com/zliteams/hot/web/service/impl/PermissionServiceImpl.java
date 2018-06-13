package com.zliteams.hot.web.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.web.dao.PermissionMapper;
import com.zliteams.hot.web.model.Permission;
import com.zliteams.hot.web.model.Role;
import com.zliteams.hot.web.service.PermissionService;

/**
 * 权限Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Set<Role> roles) {
        return permissionMapper.selectPermissionsByRoleId(roles);
    }

	@Override
	public Set<String> findPermissions(Long userId) {
		return permissionMapper.findPermissions(userId);
	}
    
}
