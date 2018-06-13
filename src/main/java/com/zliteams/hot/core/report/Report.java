package com.zliteams.hot.core.report;

import java.util.List;

import com.zliteams.hot.core.query.QueryCondition;
import com.zliteams.hot.core.field.Field;
import com.zliteams.hot.core.generic.EntityType;

public class Report {

	private String name;

	// 查询表名
	private EntityType entity;
	
	// 查询条件
	private List<QueryCondition> conditions;
	
	// 分组字段
	private List<Field> groupFields;
	
	// 统计字段
	private List<Field> statisticsFields;
	
	// 排序字段
	private List<Field> orderFields;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public EntityType getEntity() {
		return entity;
	}
	
	public void setEntity(EntityType entity) {
		this.entity = entity;
	}
	
	public List<QueryCondition> getConditions() {
		return conditions;
	}
	
	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}
	
	public List<Field> getGroupFields() {
		return groupFields;
	}
	
	public void setGroupFields(List<Field> groupFields) {
		this.groupFields = groupFields;
	}
	
	public List<Field> getStatisticsFields() {
		return statisticsFields;
	}
	
	public void setStatisticsFields(List<Field> statisticsFields) {
		this.statisticsFields = statisticsFields;
	}
	
	public List<Field> getOrderFields() {
		return orderFields;
	}
	
	public void setOrderFields(List<Field> orderFields) {
		this.orderFields = orderFields;
	}
}
