package com.zliteams.hot.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zliteams.hot.core.SpringContext;
import com.zliteams.hot.core.entity.IdGenerator;
import com.zliteams.hot.core.entity.JSONResult;
import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.field.Field;
import com.zliteams.hot.core.field.FieldType;
import com.zliteams.hot.core.generic.EntityType;
import com.zliteams.hot.core.query.DateGroupType;
import com.zliteams.hot.core.query.OrderType;
import com.zliteams.hot.core.query.Query;
import com.zliteams.hot.core.query.QueryCondition;
import com.zliteams.hot.core.query.QueryCondition.CompareType;
import com.zliteams.hot.core.query.StatisticsType;
import com.zliteams.hot.core.report.Report;
import com.zliteams.hot.core.util.ShiroHash;
import com.zliteams.hot.web.dao.BillMapper;
import com.zliteams.hot.web.model.Bill;
import com.zliteams.hot.web.model.Category;
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
	private BillMapper billMapper;

    /**
     * 用户登录
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
            	logger.info(subject.getSession().getAttribute("userInfo").toString());
                return "redirect:bill";
            }
            if (result.hasErrors()) {
                model.addAttribute("error", "参数错误！");
                return "login";
            }
            // 身份验证
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            token.setRememberMe(true);
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
        return "redirect:bill";
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
     * 用户登录
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
    @RequestMapping(value = "/admin",produces="text/html;charset=UTF-8")
    @ResponseBody
    @RequiresRoles(value = RoleSign.ADMIN)
    public String admin() {
    	Subject subject = SecurityUtils.getSubject();
    	logger.debug(subject.isRemembered()+"==============isRemembered");
    	logger.debug(subject.isAuthenticated()+"=================isAuthenticated");
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
    
    /**
     * 基于权限标识的权限控制案例
     */
    @RequestMapping(value = "/json")
    @ResponseBody
    @RequiresPermissions(value = PermissionSign.USER_CREATE)
    public List<Map<String, Object>> json(String value) {
    	Report report = new Report();
    	
    	report.setEntity(EntityType.bill);
    	
    	// 组装查询条件
//    	List<QueryCondition> conditions = new ArrayList<>();
//		QueryCondition condition = new QueryCondition();
//		Field field = new Field();
//		field.setFieldId("type");
//		field.setFieldType(FieldType.Number);
//		condition.setField(field);
//		condition.setCompareType(CompareType.range);
//		condition.setFieldValue("0~1");
//		conditions.add(condition);
//		QueryCondition condition2 = new QueryCondition();
//		Field field2 = new Field();
//		field2.setFieldId("create_time");
//		field2.setFieldType(FieldType.Date);
//		condition2.setField(field2);
//		condition2.setCompareType(CompareType.range);
//		condition2.setFieldValue("2018-05-12~2018-06-12");
//		conditions.add(condition2);
//		QueryCondition condition3 = new QueryCondition();
//		Field field3 = new Field();
//		field3.setFieldId("money");
//		field3.setFieldType(FieldType.Text);
//		condition3.setField(field3);
//		condition3.setCompareType(CompareType.like);
//		condition3.setFieldValue("2");
//		conditions.add(condition3);
//		report.setConditions(conditions);
		
		// 组装分组字段
		List<Field> groupFields = new ArrayList<>();
		Field groupField1 = new Field();
		groupField1.setFieldId("type");
		groupFields.add(groupField1);
		Field groupField2 = new Field();
		groupField2.setFieldId("create_time");
		groupField2.setFieldType(FieldType.Date);
		groupField2.setDateGroupType(DateGroupType.day);
		groupFields.add(groupField2);
		report.setGroupFields(groupFields);
		
		// 组装统计字段
		List<Field> statisticsFields = new ArrayList<>();
		Field statisticsField = new Field();
		statisticsField.setFieldId("money");
		statisticsField.setStatisticsType(StatisticsType.sum);
		statisticsFields.add(statisticsField);
//		Field statisticsField2 = new Field();
//		statisticsField2.setFieldId("money");
//		statisticsField2.setStatisticsType(StatisticsType.max);
//		statisticsFields.add(statisticsField2);
//		Field statisticsField3 = new Field();
//		statisticsField3.setFieldId("money");
//		statisticsField3.setStatisticsType(StatisticsType.min);
//		statisticsFields.add(statisticsField3);
//		Field statisticsField4 = new Field();
//		statisticsField4.setFieldId("money");
//		statisticsField4.setStatisticsType(StatisticsType.avg);
//		statisticsFields.add(statisticsField4);
//		Field statisticsField1 = new Field();
//		statisticsField1.setFieldId("id");
//		statisticsField1.setStatisticsType(StatisticsType.count);
//		statisticsFields.add(statisticsField1);
		report.setStatisticsFields(statisticsFields);
		
		// 排序字段
		List<Field> orderFields = new ArrayList<>();
		Field orderField1 = new Field();
		String idStr1 = "gf_type";
		orderField1.setFieldId(idStr1);
		orderField1.setOrderType(OrderType.asc);
		orderFields.add(orderField1);
		Field orderField = new Field();
		String idStr = StatisticsType.sum + "_money";
		orderField.setFieldId(idStr);
		orderField.setOrderType(OrderType.desc);
		orderFields.add(orderField);
		report.setOrderFields(orderFields);
		
		List<Map<String, Object>> result = billMapper.getReport(report);
		
        return result;
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
    

    @RequestMapping("/bill")
    public String bill(Model model) {
    	try {
    		Category category = new Category();
    		category.setType(0);
    		List<Category> categorys = categoryService.selectList(category);
    		model.addAttribute("categorys", categorys);
    		
    		ObjectMapper mapper = new ObjectMapper();
			String categorysStr = "var categorys = " + mapper.writeValueAsString(categorys);
			model.addAttribute("categorysStr", categorysStr);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "bill";
    }
    
    @RequestMapping(value = "/createBill", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Bill> createBill(Bill bill) {
    	JSONResult<Bill> jsonResult = new JSONResult<>();
    	Subject subject = SecurityUtils.getSubject();
    	User user = (User) subject.getSession().getAttribute("userInfo");
    	bill.setCreateTime(new Date());
    	bill.setUser(user.getId());
    	billService.insert(bill);
    	jsonResult.setData(bill);
    	return jsonResult;
    }
    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Category> createCategory(Category category) {
    	JSONResult<Category> jsonResult = new JSONResult<>();
    	try {
    		category.setCreateTime(new Date());
    		categoryService.insert(category);
    		jsonResult.setData(category);
    		jsonResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setStatusCode(500);
			jsonResult.setSuccess(false);
		}
    	return jsonResult;
    }
    @RequestMapping(value = "/getCategorys", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<List<Category>> getCategorys(Category category) {
    	JSONResult<List<Category>> jsonResult = new JSONResult<>();
    	try {
    		List<Category> categorys = categoryService.selectList(category);
    		jsonResult.setData(categorys);
    		jsonResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setStatusCode(500);
			jsonResult.setSuccess(false);
		}
    	return jsonResult;
    }
    @RequestMapping(value = "/getBillPage")
    @ResponseBody
    public JSONResult<List<Bill>> getBillPage(Bill bill, @RequestParam(required=false) Integer pageNo, @RequestParam(required=false) Integer pageSize) {
    	JSONResult<List<Bill>> jsonResult = new JSONResult<>();
    	try {
    		if(pageNo == null || pageNo==0) pageNo=1;
    		if(pageSize == null || pageSize==0) pageSize=10;
    		Page<Bill> page = new Page<>(pageNo, pageSize);
    		List<Bill> bills = billService.selectByPage(page, bill);
    		jsonResult.setData(bills);
    		jsonResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setStatusCode(500);
			jsonResult.setSuccess(false);
		}
    	return jsonResult;
    }
}
