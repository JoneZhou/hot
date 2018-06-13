package com.zliteams.hot.core.field;

import com.zliteams.hot.core.generic.GenericEnum;

public enum FieldType implements GenericEnum {
	Text("文本"), Id("id"), Date("时间"), Number("数字");
	
	private String text;
	
	FieldType(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return this.text;
	}
	
	public static void main(String[] args) {
		System.out.println(FieldType.Text.name());
	}

}
