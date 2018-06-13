package com.zliteams.hot.core.query;

import java.io.Serializable;
import java.util.List;

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
	private String orderField;
	/**
	 * 是否升序
	 */
	private Boolean asc;
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

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public Boolean getAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}
}