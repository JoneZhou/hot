package com.zliteams.hot.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.web.dao.RoleMapper;
import com.zliteams.hot.web.model.Role;
import com.zliteams.hot.web.service.RoleService;

/**
 * 角色Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午4:16:33
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public GenericDao<Role, Long> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }
    
    @Override
    public Set<String> findRoles(Long userId) {
        return roleMapper.findRoles(userId);
    }

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		// TODO Auto-generated method stub
		
	}

}
