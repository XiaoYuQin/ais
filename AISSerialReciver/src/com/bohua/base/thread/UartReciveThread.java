package com.bohua.base.thread;

import java.io.BufferedReader;

import junit.framework.AssertionFailedError;

import com.bohua.HDJni.JniUart;
import com.disgen.aisSerialReciver.AES;
import com.disgen.aisSerialReciver.AIS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UartReciveThread extends Thread{

	BufferedReader br = null; 
	 Handler handler;
String aes= "123456";//这里也是默认密码
	public UartReciveThread(Handler handler,String aes){
		this.handler= handler;
		this.aes = aes;
	}
	public void init(String aes){
		//这里重置了AES的密码
		this.aes = aes;
	}
	
	String data;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i("UartReciveThread","run");
		while(true){
//			
		try{
			
			data+=JniUart.jniReadUart();
			if(data.length() > 200) data = "";
			int flag = data.indexOf("\n");
			if(flag != -1){
								
				
				String aesData = data.substring(0, flag);	
				Log.i("ClientThread","data = "+aesData);
				data = "";
				String aisData = AES.decrypt(aesData, this.aes);
				if(aisData!=null){
					
					
					Log.i("ClientThread","aesData = "+aisData);
					AIS ais = new AIS();
					ais.parse(aisData);		
					
					if(ais.msgType == 1){
						Message msg = new Message();    
						msg.what = 1;    
						Bundle bundle = new Bundle();
						
						bundle.putInt("nav_status"	, ais.msg1.nav_status());
						bundle.putInt("rot"			, ais.msg1.rot());
						bundle.putInt("sog"			, ais.msg1.sog());
						bundle.putInt("pos_acc"		, ais.msg1.pos_acc());
						bundle.putLong("latitude"	, ais.msg1.latitude());
						bundle.putLong("longitude"	, ais.msg1.longitude());
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
						bundle.putLong("latitude"	, ais.msg1.latitude());
						bundle.putLong("longitude"	, ais.msg1.longitude());
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
			}
			
		}catch(Exception e){e.printStackTrace();}
		catch(AssertionFailedError e){e.printStackTrace();}
		

		
			
			
			 
			
		}
	}
	
	
}
