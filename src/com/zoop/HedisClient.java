package com.zoop;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(password);
			writer.newLine();
			writer.write("SET");
			writer.newLine();
			writer.write(time);
			writer.newLine();
			writer.write(key);
			writer.newLine();
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bao);
			oos.writeObject(value);
			writer.write(bao.toByteArray().toString());
			writer.flush();
			writer.close();
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
			OutputStream out = socket.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(password);
			writer.newLine();
			writer.write("GET");
			writer.newLine();
			writer.write(key);
			writer.flush();
			writer.close();
			InputStream in = socket.getInputStream();
			byte[] buf = new byte[in.available()];
			in.read(buf);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
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
