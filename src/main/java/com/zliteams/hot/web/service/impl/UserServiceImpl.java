package com.zliteams.hot.web.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zliteams.hot.core.entity.IdGenerator;
import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.core.util.PasswordHash;
import com.zliteams.hot.web.dao.UserMapper;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.model.UserExample;
import com.zliteams.hot.web.service.RoleService;
import com.zliteams.hot.web.service.UserService;

/**
 * 用户Service实现类
 *
 * @author StarZou
 * @since 2014年7月5日 上午11:54:24
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
    public int insert(User model) {
    	if(model.getId() == null) {
    		model.setId(idGenerator.generate());
    	}
        return userMapper.insertSelective(model);
    }

    @Override
    public int update(User model) {
        return userMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User authentication(User user) {
        return userMapper.authentication(user);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public GenericDao<User, Long> getDao() {
        return userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        final List<User> list = userMapper.selectByExample(example);
        if(list.size() == 0) return null;
        return list.get(0);
    }

}
