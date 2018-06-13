package com.zliteams.hot.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.web.model.User;

/**
 * 用户Dao接口
 * 
 **/
public interface UserMapper extends GenericDao<User, Long> {
    /**
     * 用户登录验证查询
     */
    User authentication(@Param("record") User record);

    /**
     * 分页条件查询
     */
    List<User> selectByPage(Page<User> page, User user);

	List<User> selectByUser(User user);
}