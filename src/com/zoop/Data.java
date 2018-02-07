package com.zoop;

import java.io.Serializable;

public class Data implements Serializable{

	private static final long serialVersionUID = -4611807147802759404L;

	private String data;
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public String toString() {
		return "data: "+data;
	}
}
