/**
 * 
 */
package com.zliteams.hot.core.query;

import com.zliteams.hot.core.generic.GenericEnum;

/**
 * @author zhouli
 *
 */
public enum OrderType implements GenericEnum {
	asc("升序"),desc("降序");

	private String text;
	
	OrderType(String text){
		this.text = text;
	}
	@Override
	public String getText() {
		return this.text;
	}

}
