package com.zoop;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * hedis客户端
 */
public class HedisClient {

	private String ip;
	
	private int port;
	
	@SuppressWarnings("unused")
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
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("SET");
			dos.writeUTF(key);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			oos.writeObject(value);
			byte[] buf = bao.toByteArray();
			dos.write(buf);
			dos.flush();
			dos.close();
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
	public Object get(String key) {
		Socket socket = null;
		try {
			socket = new Socket(ip,port);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("GET");
			out.writeUTF(key);
			out.flush();
			socket.shutdownOutput();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			return ois.readObject();
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
