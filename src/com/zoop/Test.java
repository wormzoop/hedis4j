package com.zoop;

public class Test {

	public static void main(String[]args) {
		HedisClient client = new HedisClient("192.168.0.103",8889,"123");
		Data data = new Data();
		data.setData("jk45645hh");
		client.set("name", data, -1);
		Data str = (Data)client.get("name");
		System.out.print(str.toString());
	}

}
