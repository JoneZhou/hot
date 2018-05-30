package com.zliteams.hot.core;

public enum RunMode {
	production, develop, test, release, beta, cd, third,fetion;

	public boolean isProduction() {
		return this.equals(production);
	}

	public boolean isDevelop() {
		return this.equals(develop);
	}

	public boolean isTest() {
		return this.equals(test);
	}

}
