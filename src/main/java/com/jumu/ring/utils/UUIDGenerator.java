package com.jumu.ring.utils;

import java.util.UUID;

public class UUIDGenerator {

	/**
	 * 随机生成32位的字符主键
	 * @return
	 */
	public static String generator(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDGenerator.generator());
	}
}
