package com.zliteams.hot.web.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zliteams.hot.core.entity.IdGenerator;
import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.web.dao.UserMapper;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.service.RoleService;
import com.zliteams.hot.web.service.UserService;

/**
 * 用户Service实现类
 *
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IdGenerator<Long> idGenerator;

    @Override
    public User authentication(User user) {
        return userMapper.authentication(user);
    }

    @Override
    public GenericDao<User, Long> getDao() {
        return userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        final List<User> list = userMapper.selectByUser(user);
        if(list.size() == 0) return null;
        return list.get(0);
    }

	@Override
	public List<User> selectByPage(Page<User> page, User user) {
		return userMapper.selectByPage(page, user);
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub
		
	}
    
}
