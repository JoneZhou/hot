package com.zliteams.hot.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zliteams.hot.core.entity.JSONResult;
import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.field.Field;
import com.zliteams.hot.core.field.FieldType;
import com.zliteams.hot.core.query.Query;
import com.zliteams.hot.core.query.QueryCondition;
import com.zliteams.hot.core.query.QueryCondition.CompareType;
import com.zliteams.hot.core.report.Report;
import com.zliteams.hot.web.model.Bill;
import com.zliteams.hot.web.model.Category;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.service.BillService;
import com.zliteams.hot.web.service.CategoryService;
import com.zliteams.hot.web.service.UserService;

/**
 * 账单
 * 
 **/
@Controller
@RequestMapping(value = "/bill")
public class BillController {
	private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private BillService billService;
    
    @RequestMapping("/bill/**")
    public String bill(Model model) {
    	try {
    		Category category = new Category();
    		category.setType(0);
    		List<Category> categorys = categoryService.selectList(category);
    		model.addAttribute("categorys", categorys);
    		logger.info(categorys.toString());
    		ObjectMapper mapper = new ObjectMapper();
			String categorysStr = "var categorys = " + mapper.writeValueAsString(categorys);
			model.addAttribute("categorysStr", categorysStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "bill";
    }

    
    @RequestMapping(value = "/createBill", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Bill> createBill(Bill bill) {
    	JSONResult<Bill> jsonResult = new JSONResult<>();
    	if(bill.getCreateTime() == null) {
    		bill.setCreateTime(new Date());
    	}
    	if(bill.getUser() == null){
    		bill.setUser(getCurrentUserId());
    	}
    	billService.insert(bill);
    	jsonResult.setData(bill);
    	jsonResult.setSuccess(true);
    	return jsonResult;
    }
    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Category> createCategory(Category category) {
    	JSONResult<Category> jsonResult = new JSONResult<>();
    	try {
    		category.setCreateTime(new Date());
    		category.setUser(getCurrentUserId());
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
    		if(category.getUser() == null){
    			category.setUser(getCurrentUserId());
    		}
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
    @RequestMapping(value = "/queryBills")
    @ResponseBody
    public JSONResult<Page<Bill>> queryBills(@RequestBody Query query) {
    	JSONResult<Page<Bill>> jsonResult = new JSONResult<>();
    	try {
    		query.setConditions(joinUserCondition(query.getConditions()));
    		Page<Bill> page = new Page<>(query.getPageNo(), query.getPageSize());
    		
    		billService.queryBills(page, query);
    		jsonResult.setData(page);
    		jsonResult.setSuccess(true);
    	} catch (Exception e) {
    		e.printStackTrace();
    		jsonResult.setStatusCode(500);
    		jsonResult.setSuccess(false);
    	}
    	return jsonResult;
    }
    @RequestMapping(value = "/getReport")
    @ResponseBody
    public JSONResult<List<Map<String, Object>>> getReport(@RequestBody Report report) {
    	JSONResult<List<Map<String, Object>>> jsonResult = new JSONResult<>();
    	try {
    		for(Field field : report.getGroupFields()) {
    			if(field.getFieldId().equals("type")) {
    				field.setFieldSql("(case when type=0 then '支出' else '收入' end)");
    			}
    		}
    		report.setConditions(joinUserCondition(report.getConditions()));
    		List<Map<String, Object>> reportResult = billService.getReport(report);
    		jsonResult.setData(reportResult);
    		jsonResult.setSuccess(true);
    	} catch (Exception e) {
    		e.printStackTrace();
    		jsonResult.setStatusCode(500);
    		jsonResult.setSuccess(false);
    	}
    	return jsonResult;
    }
    
    private Long getCurrentUserId(){
    	Subject subject = SecurityUtils.getSubject();
    	User user = (User) subject.getSession().getAttribute("userInfo");
    	return user.getId();
    }
    
    private List<QueryCondition> joinUserCondition(List<QueryCondition> conditions){
    	if(conditions == null){
    		conditions = new ArrayList<QueryCondition>();
    	}
		QueryCondition userCondition = new QueryCondition();
		Field field = new Field();
		field.setFieldId("user");
		field.setFieldType(FieldType.Number);
		userCondition.setCompareType(CompareType.eq);
		userCondition.setFieldValue(getCurrentUserId().toString());
		userCondition.setField(field);
		conditions.add(userCondition);
		return conditions;
    }
    
}
