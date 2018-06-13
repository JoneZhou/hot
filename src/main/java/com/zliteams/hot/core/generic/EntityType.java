package com.zliteams.hot.core.generic;

public enum EntityType implements GenericEnum {
	bill("账单");
	
	private String text;
	
	EntityType(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return this.text;
	}
	
	public static void main(String[] args) {
		System.out.println(EntityType.bill.getText());
	}

}
