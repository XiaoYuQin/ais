package com.disgen.aisSerialSender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.bohua.HDJni.JniUart;

public class TcpServer extends Thread{
	
	private List<String> msgList;
	//�����������AES����
	String aesSercury;
	JniUart uart;
	
	
	private void debug(String s){
		Log.i("TcpServer", s);
	}
	
	TcpServer(){		 
		Log.i("TcpServer", "TcpServer");
		msgList = new ArrayList<String>();
		
		uart = new JniUart();
		uart.init("115200");
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
 		
 		while(true){
 			
 			synchronized(msgList){
    			if(msgList.size()>0){
    				String str = msgList.get(0);
    				msgList.remove(0);
//    				debug("str = "+str+"len = "+str.length());
    				//��AES�������
    				String aesDecodecStr = AES.encrypt(str, aesSercury);
    				aesDecodecStr = aesDecodecStr + "\n";
    				//��AES������ܺ�����ݷ��ͳ�ȥ
    				uart.write(aesDecodecStr, aesDecodecStr.length());
    			}
 			}
 		}
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
        	
//            //����ServerSocket   
//            ServerSocket serverSocket = new ServerSocket(5554);   
//            while (true)   
//            {   
//                //���ܿͻ�������   
//                Socket client = serverSocket.accept();   
//                System.out.println("accept");   
//                try  
//                {   
//                    //���տͻ�����Ϣ   
////	                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));   
////	                    String str = in.readLine();   
////	                    System.out.println("read:" + str);     
//                    //�������������Ϣ   
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
//                    //�ر���   
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
//                    //�ر�   
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
