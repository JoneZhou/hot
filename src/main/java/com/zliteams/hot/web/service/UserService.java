package com.zliteams.hot.web.service;

import java.util.List;
import java.util.Set;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.generic.GenericService;
import com.zliteams.hot.web.model.User;

/**
 * 用户 业务 接口
 * 
 **/
public interface UserService extends GenericService<User, Long> {

    /**
     * 用户验证
     * 
     * @param user
     * @return
     */
    User authentication(User user);

    /**
     * 根据用户名查询用户
     * 
     * @param username
     * @return
     */
    User selectByUsername(String username);
    
	List<User> selectByPage(Page<User> page, User user);

	/**
	 * 添加用户-角色关系
	 * @param userId
	 * @param roleIds
	 */
	public void correlationRoles(Long userId, Long... roleIds);

	/**
	 * 移除用户-角色关系
	 * @param userId
	 * @param roleIds
	 */
	public void uncorrelationRoles(Long userId, Long... roleIds);
}
