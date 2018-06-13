/**
 * 
 */
package com.zliteams.hot.core.query;

import com.zliteams.hot.core.generic.GenericEnum;

/**
 * @author zhouli
 *
 */
public enum StatisticsType implements GenericEnum {
	count("记录数"),sum("总和"), max("最大值"), min("最小值"), avg("平均值");

	private String text;
	
	StatisticsType(String text){
		this.text = text;
	}
	@Override
	public String getText() {
		return this.text;
	}

}
