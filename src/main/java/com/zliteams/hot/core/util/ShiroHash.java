package com.zliteams.hot.core.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroHash {
	public static final String ALGORITHM_NAME = "md5";
	
	public static final String DEFAULT_SALT1 = "4fc27SD572cE1868";
	
	public static final String DEFAULT_SALT2 = "f079FF0338fb7574";
	
	public static final int HASH_ITERATIONS = 2;
	
	public static String encodedPassword(String password,String salt1, String salt2) {
		if(salt1 == null) {
			salt1 = ShiroHash.DEFAULT_SALT1;
		}
		if(salt2 == null) {
			salt2 = ShiroHash.DEFAULT_SALT2;
		}
    	SimpleHash hash = new SimpleHash(ALGORITHM_NAME, password, salt1 + salt2, HASH_ITERATIONS);  
		return hash.toHex();
	}
	
	public static void main(String[] args) {
		System.out.println(ShiroHash.encodedPassword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "starzou", DEFAULT_SALT2));
	}
}
