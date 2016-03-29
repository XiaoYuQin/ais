package com.disgen.aisSerialReciver;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;

import com.bohua.HDJni.Jni485;
import com.bohua.HDJni.JniUart;
import com.bohua.base.thread.UartReciveThread;

public class TcpServer extends Thread{
	
	private List<String> msgList;
	String aesSercury;
//	Jni485 uart;
	 
	
	private void debug(String s){
		Log.i("TcpServer", s);
	}
	
	TcpServer(Handler handler){		
		Log.i("TcpServer", "TcpServer");
		msgList = new ArrayList<String>();
		
//		uart = new Jni485();
//		uart.init("9600");
		JniUart.jniSetUartPara();								/*初始化串口*/
//		JniUart.write("1234", "1234".length());
//		new Thread(new UartReciveThread(handler)).start();								/*打开串口接收子线程*/

//		uart.start(); 
	} 
	public void setList(String str){
		synchronized(msgList){
//			Log.i("TcpServer", "str = "+str);
			msgList.add(str);
		}		
	}
	public void setAESsectury(String aes){
		this.aesSercury = aes; 
	}
 	public void run()   
    {   
 		debug("TcpServer run"); 
// 		uart.write("123456".toCharArray(), "123456".length());
// 		uart.read();
// 		 uart.read();
  		debug("TcpServer run"); 
 		while(true){
// 	 		 uart.read();
 	  		debug("TcpServer run"); 
// 			String tmp = uart.read();
// 			uart.ret();
//// 			debug("tmp = "+tmp);
// 			int[] aa =new int[100];
// 			aa = uart.ret();
// 			for(int i= 0;i<aa.length;i++)
// 			{
// 				debug("a"+i+" = "+aa[i]);
// 				
// 			}
// 			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) { 
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
// 			uart.write("12345","12345".length());
 		}
 		
// 		while(true){
// 			
// 			synchronized(msgList){
//    			if(msgList.size()>0){
//    				String str = msgList.get(0);
//    				msgList.remove(0);
////    				debug("str = "+str+"len = "+str.length());
//    				String aesDecodecStr = AES.encrypt(str, aesSercury);
//    				aesDecodecStr = aesDecodecStr + "\n";
//    				uart.write(aesDecodecStr, aesDecodecStr.length());
//    			}
// 			}
// 		}
//        try  
//        {
//        	
//        	while(true){
//	        	synchronized(msgList){
//	    			if(msgList.size()>0){
//	    				String str = msgList.get(0);
//	    				Log.i("TcpServer", "str = "+str);
//	    				String aesDecodecStr = AES.encrypt(str, aesSercury);
//	    				
//	    				msgList.remove(0);
//	    				System.out.println("aes = "+aesDecodecStr);  
//	    				uart.write(aesDecodecStr, aesDecodecStr.length());
//	    				
//	    			}
//	    		}		                    	
//        	}
//        	
        	
//            //创建ServerSocket   
//            ServerSocket serverSocket = new ServerSocket(5554);   
//            while (true)   
//            {   
//                //接受客户端请求   
//                Socket client = serverSocket.accept();   
//                System.out.println("accept");   
//                try  
//                {   
//                    //接收客户端消息   
////	                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
////	                    String str = in.readLine();   
////	                    System.out.println("read:" + str);     
//                    //向服务器发送消息   
//                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);         
////	                    out.println("server message"); 
//                    while(true)
//                    {
//                    	synchronized(msgList){
//                			if(msgList.size()>0){
//                				String str = msgList.get(0);
//                				String aesDecodecStr = AES.encrypt(str, aesSercury);
//                				
//                				msgList.remove(0);
//                				out.println(aesDecodecStr); 
//                			}
//                		}		                    	
//                    }
//                    //关闭流   
////	                    out.close();   
////	                    in.close();    
//                }   
//                catch (Exception e)   
//                {   
//                    System.out.println(e.getMessage());   
//                    e.printStackTrace();   
//                }   
//                finally  
//                {   
//                    //关闭   
//                    client.close();   
//                    System.out.println("close");   
//                }   
//            }   
//        }   
//        catch (Exception e)   
//        {   
//            System.out.println(e.getMessage());   
//        }   
    }   
}
