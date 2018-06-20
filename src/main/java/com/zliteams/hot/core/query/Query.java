package com.zliteams.hot.core.query;

import java.io.Serializable;
import java.util.List;

import com.zliteams.hot.core.field.Field;

/**
 * 查询
 */
public class Query implements Serializable {
	private static final long serialVersionUID = -4865770518826254472L;
	/**
	 * 页码
	 */
	private Integer pageNo;
	/**
	 * 每页显示条数
	 */
	private Integer pageSize;
	/**
	 * 排序字段
	 */
	private List<Field> orderFields;
	/**
	 * 条件
	 */
	private List<QueryCondition> conditions;
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<Field> getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(List<Field> orderFields) {
		this.orderFields = orderFields;
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}
}