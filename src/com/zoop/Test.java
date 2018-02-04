package com.zoop;

public class Test {

	public static void main(String[]args) {
		HedisClient client = new HedisClient("localhost",8888,"123");
		String name = (String)client.get("name");
		System.out.println(name);
	}
	
}
