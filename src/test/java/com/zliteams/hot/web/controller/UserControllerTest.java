package com.zliteams.hot.web.controller;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.zliteams.hot.core.feature.test.TestSupport;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.service.BillService;
import com.zliteams.hot.web.service.CategoryService;
import com.zliteams.hot.web.service.UserService;

public class UserControllerTest extends TestSupport {
	@Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private BillService billService;

	@Test
	public void testLogin() {
		try {
            final User authUserInfo = userService.selectByUsername("admin");
            logger.info(authUserInfo);
        } catch (AuthenticationException e) {
            // 身份验证失败
            logger.error("用户名或密码错误 ！");
        }
	}

	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegist() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testJson() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetList() {
		fail("Not yet implemented");
	}

	@Test
	public void testBill() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateBill() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCategorys() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBillPage() {
		fail("Not yet implemented");
	}

}
