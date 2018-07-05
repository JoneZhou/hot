package com.zliteams.hot.web.controller;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zliteams.hot.core.entity.JSONResult;
import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.util.CookieUtils;
import com.zliteams.hot.core.util.ShiroHash;
import com.zliteams.hot.web.dao.BillMapper;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.security.PermissionSign;
import com.zliteams.hot.web.security.RoleSign;
import com.zliteams.hot.web.service.BillService;
import com.zliteams.hot.web.service.CategoryService;
import com.zliteams.hot.web.service.UserService;

/**
 * 用户控制器
 * 
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private BillService billService;
    @Resource
    private org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager;
    
    @Resource
	private BillMapper billMapper;

    /**
     * 用户登录
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request,HttpServletResponse response) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
            	logger.info(subject.getSession().getAttribute("userInfo").toString());
                return "redirect:/rest/bill/bill";
            }
            if (result.hasErrors()) {
                model.addAttribute("error", "参数错误！");
                return "login";
            }
            // 身份验证
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
//            token.setRememberMe(true);
            subject.login(token);
            // 验证成功在Session中保存用户信息
            final User authUserInfo = userService.selectByUsername(user.getUsername());
            request.getSession().setAttribute("userInfo", authUserInfo);
            
        } catch (AuthenticationException e) {
            // 身份验证失败
            model.addAttribute("error", "用户名或密码错误 ！");
            logger.error("用户名或密码错误 ！");
            return "login";
        }
        return "redirect:/rest/bill/bill";
    }

    /**
     * 用户登出
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    
    /**
     * 用户注册
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String regist(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
    	// 加密
    	String username = user.getUsername();
    	String password = user.getPassword();
    	String salt1 = username;  
    	String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
    	salt2 = ShiroHash.DEFAULT_SALT2;// 盐2需要保存在数据库，在这里用固定的盐2
    	user.setPassword(ShiroHash.encodedPassword(password, salt1, salt2));
    	userService.insert(user);
        return "template";
    }

    /**
     * 基于角色 标识的权限控制案例
     */
    @RequestMapping(value = "/admin")
    @ResponseBody
    public String admin(HttpServletRequest request) {
    	logger.info(request.getSession().getId());
    	Subject subject = SecurityUtils.getSubject();
    	String sessionId = CookieUtils.getCookie(request, "HOTID");
    	sessionId = request.getParameter("HOTID");
    	System.out.println(request.getParameter("HOTID"));
    	org.apache.shiro.session.Session session = securityManager.getSession(new DefaultSessionKey(sessionId));
    	logger.debug(subject.isRemembered()+"==============isRemembered");
    	logger.debug(subject.isAuthenticated()+"=================isAuthenticated");
    	logger.debug(session.getId()+"=================sessionId");
    	logger.debug(session.getAttribute("userinfo")+"=================sessionId");
        return "拥有admin角色,能访问";
    }

    /**
     * 基于权限标识的权限控制案例
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    @RequiresPermissions(value = {PermissionSign.USER_CREATE,PermissionSign.USER_DELETE})
    public String create() {
        return "拥有user:create权限,能访问";
    }
    
    @RequestMapping(value = "/getList")
    @ResponseBody
    @RequiresRoles(value = RoleSign.ADMIN)
    public JSONResult<Page<User>> getList() {
    	JSONResult<Page<User>> jsonResult = new JSONResult<>();
    	Page<User> page = new Page<User>(1, 10);
    	User user = new User();
    	user.setState("1");
    	userService.selectByPage(page, user);
    	jsonResult.setData(page);
    	jsonResult.setSuccess(true);
    	jsonResult.setMessage("成功");
    	return jsonResult;
    }
    
}
