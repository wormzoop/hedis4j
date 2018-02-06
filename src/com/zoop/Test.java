package com.zoop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Test {

	public static void main(String[]args) {
		HedisClient client = new HedisClient("192.168.0.103",8889,"123");
		client.set("name", "jijijiij", -1);
//		test();
	}

	public static void test() {
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			User user = new User();
			user.setName("123");
			oos.writeObject(user);
			byte[] buf = bao.toByteArray();
			System.out.println(Arrays.toString(buf));
			ByteArrayInputStream bai = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(bai);
			Object obj = ois.readObject();
			User u = (User)obj;
			System.out.println(u.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class User implements Serializable{
		private static final long serialVersionUID = -776848160063661771L;
		private String name;
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String toString() {
			return "name: "+name;
		}
	}
	
}
