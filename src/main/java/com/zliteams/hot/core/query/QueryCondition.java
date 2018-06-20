package com.zliteams.hot.core.query;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zliteams.hot.core.field.Field;
import com.zliteams.hot.core.field.FieldType;

/**
 * 查询条件
 */
public class QueryCondition implements Serializable {
	private static final long serialVersionUID = -8292420090751904970L;
	
	private Field field;
	/**
	 * 比较类型
	 */
	private CompareType compareType;
	/**
	 * 字段值
	 */
	private String fieldValue;
	/**
	 * 比较类型为range时，属性值拆分成开始与结束值
	 */
	private Object beginValue;
	private Object endValue;
	/**
	 * 比较类型为RadioBox、CheckBox、Select或Employee时，值为id集合
	 */
	private List<Long> idValues;


	/**
	 * 比较类型
	 */
	public static enum CompareType {
		// 适用于字符类型
		like, notLike,
		// 适用于字符、数字、选择框或员工类型
		eq, neq,
		// 适用于数字类型
		range, gt, gtAndEq, lt, ltAndEq,
		// 适用于所有类型
		empty, notEmpty
	}

	/**
	 * 字段类型-字段值正则表达式
	 */
	public static String numberRange = "([-]?[0-9]+[~][-]?[0-9]+|[-]?[0-9]+[~][*]|[*][~][-]?[0-9]+)";
	public static String numberOther = "[-]?[0-9]+";
	public static String dateRange = "([0-9a-zA-Z]+[~][0-9a-zA-Z]+)";
	/**
	 * 日期类型字段值格式化
	 */
	public static SimpleDateFormat commonDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public String getCompareType() {
		return compareType == null ? null : compareType.toString();
	}

	public void setCompareType(CompareType compareType) {
		this.compareType = compareType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Object getBeginValue() {
		if (fieldValue != null && !fieldValue.trim().isEmpty()) {
			if (field.getFieldType() == FieldType.Date || field.getFieldType() == FieldType.Number) {
				if (fieldValue != null && !fieldValue.trim().isEmpty()) {
					fieldValue = fieldValue.trim();
					beginValue = fieldValue.substring(0, fieldValue.indexOf("~"));
				}
			}
		}
		return beginValue;
	}

	public Object getEndValue() {
		if (fieldValue != null && !fieldValue.trim().isEmpty()) {
			if (field.getFieldType() == FieldType.Date || field.getFieldType() == FieldType.Number) {
				if (fieldValue != null && !fieldValue.trim().isEmpty()) {
					fieldValue = fieldValue.trim();
					endValue = fieldValue.substring((fieldValue.indexOf("~")+1),fieldValue.length());
				}
			}
		}
		return endValue;
	}

	public List<Long> getIdValues() {
		if (fieldValue != null && !fieldValue.trim().isEmpty()) {
			if (field.getFieldType() == FieldType.Id) {
				String[] idArray = fieldValue.trim().split(",");
				List<Long> temp = new ArrayList<Long>();
				for (String id : idArray) {
					temp.add(Long.parseLong(id));
				}
				idValues = temp;
			}
		}
		return idValues;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	public static void main(String[] args) {
		String a = "~";
		String b = a.substring(0, a.indexOf("~"));
		String c = a.substring((a.indexOf("~")+1), a.length());
		System.out.println("index:" + a.indexOf("~") );
		System.out.println("b:" + (b==null?b:"null") );
		System.out.println("c:" + (c==null?c:"null") );
	}
}