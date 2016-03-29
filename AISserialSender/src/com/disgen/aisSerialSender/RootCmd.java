package com.disgen.aisSerialSender;


import java.io.BufferedReader;
import java.io.DataOutputStream;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;  

import android.util.Log;


public final class RootCmd {

	//执行linux命令但不关注结果输出  
	  public static void execRootCmdSilent(String paramString)  
	  {  
	    try  
	    {  
	      Process localProcess = Runtime.getRuntime().exec("su");  
	      Object localObject = localProcess.getOutputStream();  
	      DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream)localObject);  
	      String str = String.valueOf(paramString);  
	      localObject = str + "\n";  
	      localDataOutputStream.writeBytes((String)localObject);  
	      localDataOutputStream.flush();  
	      localDataOutputStream.writeBytes("exit\n");  
	      localDataOutputStream.flush();  
	      localProcess.waitFor();  
	      localObject = localProcess.exitValue();  
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }   
	  }  
	  
	  public static void exeShell(String cmd){
		   try{
		            Process p = Runtime.getRuntime().exec(cmd);         
		            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));        
		            Log.i("exeShell",cmd);     
		            String line = null;          
		            while ((line = in.readLine()) != null) {           
		            	Log.i("exeShell",line);                  
		            }                
		    }
		    catch(IOException t){
		    	Log.i("exeShell","fail");       
		    	t.printStackTrace();
		    }   
		}
	  
	  
	
}
