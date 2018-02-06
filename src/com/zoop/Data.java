package com.zoop;

import java.io.Serializable;

public class Data implements Serializable{

	private static final long serialVersionUID = -4611807147802759404L;

private String key;
	
	private String time;
	
	private String password;
	
	private Object value;

	private String type;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
