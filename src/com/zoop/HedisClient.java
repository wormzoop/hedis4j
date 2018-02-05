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
			socket.shutdownOutput();
			InputStream in = socket.getInputStream();
			int len;
			byte[] resbuf = null;
			byte[] buf = new byte[1024 * 1024];
			while((len = in.read(buf)) != -1) {
				if(resbuf != null) {
					byte[] temp = new byte[resbuf.length];
					System.arraycopy(resbuf, 0, temp, 0, len);
					resbuf = new byte[temp.length+len];
					System.arraycopy(temp, 0, resbuf, 0, temp.length);
					System.arraycopy(buf, 0, resbuf, temp.length, len);
				}else {
					resbuf = new byte[len];
					System.arraycopy(buf,0,resbuf,0,len);
				}
			}
			if(resbuf != null) {
				String str = new String(resbuf);
				System.out.println(str);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf));
				return ois.readObject();
			}else {
				return null;
			}
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
