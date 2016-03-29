package com.bohua.HDJni;

import android.util.Log;

public class JniUart extends Thread{

	public static int init_state = -1;
	public static String read()
	{
		return jniReadUart();
	}
	public static int write(String str,int len)
	{
		if(init_state == -1) return init_state;
		return jniWriteUart(str,len);
	}
	public static int init(String baud)
	{
		return init_state = jniSetUartPara();
	}

//	public void run(){
//		
//		while(true){
//			try {
//				Thread.sleep(2000);
//				Log.i("JniUart","22222");
//				write("22222","22222".length());				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	
	/*** JNI ***/
	/*初始化串口：：波特率等*/
	public native static int jniSetUartPara();
    /*写串口数据*/
    public native static int jniWriteUart(String data,int len);
    /*读串口数据*/
	public native static String jniReadUart();   

    static {
        System.loadLibrary("uart");
    }
	
}
