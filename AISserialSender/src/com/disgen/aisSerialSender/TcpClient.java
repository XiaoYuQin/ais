package com.disgen.aisSerialSender;

import java.io.BufferedReader;
//import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import com.bohua.HDJni.JniUart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TcpClient extends Thread{
	
	String ip;
	int port;
	Handler handler;
	String aesSercury;
	TcpServer server;
	public boolean isConnected;

	public TcpClient(){

		
		
	}
	
	private void debug(String s){
		Log.i("TcpClient", s);
	}
	public void intTcpServer(TcpServer server){
		this.server = server;
		
	}
	public void setAESsectury(String aes){			
			this.aesSercury = aes;
			debug("setAes!!!!!");
	}
	public void init(String ip,Handler handler){
		this.ip = ip;
		this.port = 8040;
		this.handler = handler;
		isConnected =false;
	}
	public void init(String ip,int port,Handler handler){
		this.ip = ip;
		this.port = port;
		this.handler = handler;
		isConnected = false;
	}
	public void run(){
		debug("run");
		while(true){
			try  
			{  
				//建立Socket   
				Socket s=new Socket(InetAddress.getByName(this.ip),this.port);  
				InputStream ips=s.getInputStream();  
//				OutputStream ops=s.getOutputStream();  
				  
//				BufferedReader brKey = new BufferedReader(new InputStreamReader(System.in));//键盘输入  
//				DataOutputStream dos = new DataOutputStream(ops);  
				BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));  
				Message msg2 = new Message();    
				msg2.what = 200;     
				handler.sendMessage(msg2);
				isConnected = true;
				debug("while");
				while(true)  
				{  
					String strWord = brNet.readLine();  
//					debug(strWord);
					server.setList(strWord);
					AIS ais = new AIS();
					ais.parse(strWord);		
					
					//网络数据进入，第一步先解密
//					try
//					{
//						if(MainActivity.machineStatus == 2)//client
//						{
//							synchronized(aesSercury){
//								
//								String aesDecodecStr = AES.decrypt(strWord, aesSercury);							
//								if(aesDecodecStr == null){
//									Message msg = new Message();    
//									msg.what = 123;    
//									handler.sendMessage(msg);
//									return;
//								}
//								//解密后，再带入AES包中换算出消息类型，以及内容							
//								ais.parse(aesDecodecStr);
//							}
//						}					
//					}
//					catch(Exception e)
//					{
//						Message msg = new Message();    
//						msg.what = 123;    
//						handler.sendMessage(msg);
//						e.printStackTrace();
//					}
//					if(MainActivity.machineStatus == 1)//server
//					{
//						server.setList(strWord);
//						ais.parse(strWord);		
//						
//
//					}
					if(ais.msgType == 1){
						Message msg = new Message();    
						msg.what = 1;    
						Bundle bundle = new Bundle();
						
						bundle.putInt("nav_status"	, ais.msg1.nav_status());
						bundle.putInt("rot"			, ais.msg1.rot());
						bundle.putInt("sog"			, ais.msg1.sog());
						bundle.putInt("pos_acc"		, ais.msg1.pos_acc());
						bundle.putLong("latitude"	, ais.msg1.pos.latitude());
						bundle.putLong("longitude"	, ais.msg1.pos.longitude());
						bundle.putInt("cog"			, ais.msg1.cog());
						bundle.putInt("true_heading", ais.msg1.true_heading());
						bundle.putInt("utc_sec"		, ais.msg1.utc_sec());
						bundle.putInt("regional"	, ais.msg1.regional());
						bundle.putInt("spare"		, ais.msg1.spare());
						bundle.putInt("raim"		, ais.msg1.raim());
						bundle.putInt("sync_state"	, ais.msg1.sync_state());
						bundle.putInt("slot_timeout", ais.msg1.slot_timeout());
						bundle.putInt("sub_message"	, ais.msg1.sub_message());
						
						bundle.putLong("userid", ais.msg1.userid());
						bundle.putInt("msgid"	, ais.msg1.msgid());
						bundle.putInt("repeat"	, ais.msg1.repeat());
						
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
					else if(ais.msgType == 5){
						Message msg = new Message();    
						msg.what = 5;    
						Bundle bundle = new Bundle();
						
						bundle.putInt("nav_status"	, ais.msg1.nav_status());
						bundle.putInt("rot"			, ais.msg1.rot());
						bundle.putInt("sog"			, ais.msg1.sog());
						bundle.putInt("pos_acc"		, ais.msg1.pos_acc());
						bundle.putLong("latitude"	, ais.msg1.pos.latitude());
						bundle.putLong("longitude"	, ais.msg1.pos.longitude());
						bundle.putInt("cog"			, ais.msg1.cog());
						bundle.putInt("true_heading", ais.msg1.true_heading());
						bundle.putInt("utc_sec"		, ais.msg1.utc_sec());
						bundle.putInt("regional"	, ais.msg1.regional());
						bundle.putInt("spare"		, ais.msg1.spare());
						bundle.putInt("raim"		, ais.msg1.raim());
						bundle.putInt("sync_state"	, ais.msg1.sync_state());
						bundle.putInt("slot_timeout", ais.msg1.slot_timeout());
						bundle.putInt("sub_message"	, ais.msg1.sub_message());
						
						bundle.putLong("userid", ais.msg5.userid());
						bundle.putInt("msgid"	, ais.msg5.msgid());
						bundle.putInt("repeat"	, ais.msg5.repeat());
						
						msg.setData(bundle);
						handler.sendMessage(msg);  
					}
				}
//				dos.close();  
//				brNet.close();  
//				brKey.close();  
//				s.close();  
			}
			catch(Exception e){e.printStackTrace();}  
//			catch(SocketException e){e.printStackTrace();}
				debug("connet ip eror");
				Message msg1 = new Message();    
				msg1.what = 201;    
				handler.sendMessage(msg1);
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();

				}
			}			
	}
	
}
