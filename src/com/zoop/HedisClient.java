package com.zoop;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * hedis客户端
 */
public class HedisClient {

	private String ip;
	
	private int port;
	
	private String password;
	
	public HedisClient() {
		super();
	}
	
	public HedisClient(String ip, int port, String password) {
		this.ip = ip;
		this.port = port;
		this.password = password;
	}
	
	/**
	 * 存数据
	 * @param key
	 * @param value
	 * @param time 毫秒
	 */
	public void set(String key, Object value, int time) {
		Socket socket = null;
		try {
			socket = new Socket(ip,port);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", key);
			map.put("type", "SET");
			map.put("value", value);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(map);
			oos.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(socket != null) {
					socket.close();
				}
			}catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * 取数据
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object get(String key) {
		Socket socket = null;
		try {
			socket = new Socket(ip,port);
			OutputStream out = socket.getOutputStream();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "GET");
			map.put("key", key);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(map);
			oos.flush();
			socket.shutdownOutput();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			return (Map<String, Object>)ois.readObject();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket != null) {
					socket.close();
				}
			}catch(Exception e) {
				
			}
		}
		return null;
	}
	
}
