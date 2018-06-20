package com.zliteams.hot.core.field;

import com.zliteams.hot.core.generic.BaseModel;
import com.zliteams.hot.core.query.DateGroupType;
import com.zliteams.hot.core.query.OrderType;
import com.zliteams.hot.core.query.StatisticsType;

public class Field extends BaseModel{
	private static final long serialVersionUID = 3316047456743017466L;

	/**
	 * 字段id
	 */
	private String fieldId;
	
	/**
	 * 字段类型
	 */
	private FieldType fieldType;
	
	/**
	 * 字段名
	 */
	private String fieldName;
	
	/**
	 * 排序方式
	 */
	private OrderType orderType;
	
	/**
	 * 统计类型
	 */
	private StatisticsType statisticsType;
	
	/**
	 * 日期分组类型
	 */
	private DateGroupType dateGroupType;
	
	/**
	 * 显示字段名
	 * 当前字段为对象时设置要查询该对象中的字段名
	 */
	private String fieldShow;
	
	/**
	 *  冗余 可自行设置改字段的查询语句，默认值为 fieldId
	 */
	private String fieldSql;
	
	
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
		this.fieldSql = fieldId; // 默认为字段名
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public StatisticsType getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(StatisticsType statisticsType) {
		this.statisticsType = statisticsType;
	}
	public DateGroupType getDateGroupType() {
		return dateGroupType;
	}
	public void setDateGroupType(DateGroupType dateGroupType) {
		this.dateGroupType = dateGroupType;
	}
	public String getFieldShow() {
		return fieldShow;
	}
	public void setFieldShow(String fieldShow) {
		this.fieldShow = fieldShow;
	}
	public String getFieldSql() {
		return fieldSql;
	}
	public void setFieldSql(String fieldSql) {
		this.fieldSql = fieldSql;
	}
}
