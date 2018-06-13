package com.zliteams.hot.web.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.zliteams.hot.core.feature.test.TestSupport;
import com.zliteams.hot.core.field.Field;
import com.zliteams.hot.core.field.FieldType;
import com.zliteams.hot.core.generic.EntityType;
import com.zliteams.hot.core.query.DateGroupType;
import com.zliteams.hot.core.query.OrderType;
import com.zliteams.hot.core.query.StatisticsType;
import com.zliteams.hot.core.report.Report;
import com.zliteams.hot.web.dao.BillMapper;
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
    @Resource
	private BillMapper billMapper;

	@Test
	public void test() {
		try {
            System.out.println(billMapper);
            Report report = new Report();
        	
        	report.setEntity(EntityType.bill);
        	
        	// 组装查询条件
//        	List<QueryCondition> conditions = new ArrayList<>();
//    		QueryCondition condition = new QueryCondition();
//    		Field field = new Field();
//    		field.setFieldId("type");
//    		field.setFieldType(FieldType.Number);
//    		condition.setField(field);
//    		condition.setCompareType(CompareType.range);
//    		condition.setFieldValue("0~1");
//    		conditions.add(condition);
//    		QueryCondition condition2 = new QueryCondition();
//    		Field field2 = new Field();
//    		field2.setFieldId("create_time");
//    		field2.setFieldType(FieldType.Date);
//    		condition2.setField(field2);
//    		condition2.setCompareType(CompareType.range);
//    		condition2.setFieldValue("2018-05-12~2018-06-12");
//    		conditions.add(condition2);
//    		QueryCondition condition3 = new QueryCondition();
//    		Field field3 = new Field();
//    		field3.setFieldId("money");
//    		field3.setFieldType(FieldType.Text);
//    		condition3.setField(field3);
//    		condition3.setCompareType(CompareType.like);
//    		condition3.setFieldValue("2");
//    		conditions.add(condition3);
//    		report.setConditions(conditions);
    		
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
//    		Field statisticsField2 = new Field();
//    		statisticsField2.setFieldId("money");
//    		statisticsField2.setStatisticsType(StatisticsType.max);
//    		statisticsFields.add(statisticsField2);
//    		Field statisticsField3 = new Field();
//    		statisticsField3.setFieldId("money");
//    		statisticsField3.setStatisticsType(StatisticsType.min);
//    		statisticsFields.add(statisticsField3);
//    		Field statisticsField4 = new Field();
//    		statisticsField4.setFieldId("money");
//    		statisticsField4.setStatisticsType(StatisticsType.avg);
//    		statisticsFields.add(statisticsField4);
//    		Field statisticsField1 = new Field();
//    		statisticsField1.setFieldId("id");
//    		statisticsField1.setStatisticsType(StatisticsType.count);
//    		statisticsFields.add(statisticsField1);
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
    		System.out.println(result);
        } catch (AuthenticationException e) {
            // 身份验证失败
            logger.error("用户名或密码错误 ！");
        }
	}

}
