package com.disgen.aisSerialSender;

import java.math.BigDecimal;

import com.bohua.HDJni.JniUart;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	public static int machineStatus = 1;//server
//	public static int machineStatus = 2;//client
	
	TextView ipText;
	Button button;
	Button button01;
	public Handler mHandler;  
	EditText ipEditText;
	EditText aesEditText;
	TcpClient tcpClient; 
	TcpServer tcpServer;
	
	TextView tvMsgId;		//����ID
	TextView tvRelayFlag;	//ת��ָʾ��
	TextView tvUserId;		//�û�ID
	TextView tvNavStatus;	//����״̬
	TextView tvlatitude;	//γ��		
	TextView tvlongitude;	//����
	TextView tvCourse;		//����
	TextView tvRotais;
	TextView tvSog;
	TextView tvCog;
	TextView tvTimestamp;	//ʱ���
	
	
	private void debug(String s){
		Log.i("main", s);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i("MainActivity","onCreate");
		super.onCreate(savedInstanceState);
		Log.i("MainActivity","setContentView");
		setContentView(R.layout.activity_main);
		
//		RootCmd.execRootCmdSilent("chmod 777 /dev/s3c_serial1");
//		RootCmd.exeShell("adb shell chmod 777 /dev/s3c_serial0");
//		RootCmd.exeShell("adb shell chmod 777 /dev/s3c_serial1");
//		RootCmd.exeShell("ls -l /dev/");

 
		
//		String content = "AEStest";
//        String password = "AEStest";
//        debug("�ܡ�Կ��" + password);
//        debug("����ǰ��" + content);
//        
//        // ����
//        String encryptResult = AES.encrypt(content, password);
//        debug("���ܺ�" + encryptResult);
//        // ����
//        String decryptResult = AES.decrypt(encryptResult, password);
//        debug("���ܺ�" + decryptResult);    
		if(machineStatus == 1)
			setTitle("��̨AIS������");
		else if(machineStatus == 2)
			setTitle("AIS������");
		
        ipText = (TextView)findViewById(R.id.TextView23);
        ipText.setText(Wifi.getLocalIpAddress(getApplicationContext()));
        
        tvMsgId = (TextView)findViewById(R.id.TextView11);
        tvRelayFlag  = (TextView)findViewById(R.id.TextView12);
        tvUserId = (TextView)findViewById(R.id.TextView13);
        tvNavStatus = (TextView)findViewById(R.id.TextView14);
    	tvlatitude = (TextView)findViewById(R.id.TextView15);		
    	tvlongitude = (TextView)findViewById(R.id.TextView16);
    	tvCourse = (TextView)findViewById(R.id.TextView17);
    	tvRotais = (TextView)findViewById(R.id.TextView18);
    	tvSog = (TextView)findViewById(R.id.TextView19);
    	tvCog = (TextView)findViewById(R.id.TextView20);
    	tvTimestamp = (TextView)findViewById(R.id.TextView21);
        		
    		
    	
        mHandler = new Handler() 
        {
        	public void handleMessage(Message msg) 
        	{    
                // process incoming messages here    
        		switch(msg.what)
        		{
        			case 1:        				        				
        				        				
        				Bundle bundle = new Bundle();
        				bundle = msg.getData();
        				
        				int latitudeDu = (int)bundle.getLong("latitude")/600000;
        				double latitudeFen = new BigDecimal((double)bundle.getLong("latitude")%600000).setScale(5,   BigDecimal.ROUND_HALF_UP).doubleValue();
        				latitudeFen = new   BigDecimal(latitudeFen/10000).setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
        				
        				int longitudeDu = (int)bundle.getLong("longitude")/600000;
        				double longitudeFen = new BigDecimal((double)bundle.getLong("longitude")%600000).setScale(5,   BigDecimal.ROUND_HALF_UP).doubleValue();
        				longitudeFen = new   BigDecimal(longitudeFen/10000).setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();        		
        				
//        				debug("msgid = "+bundle.getInt("msgid"));
//        				debug("repeat = "+bundle.getInt("repeat"));
//        				debug("userid = "+bundle.getLong("userid"));
//        				debug("nav_status = "+bundle.getInt("nav_status"));
//
//        				debug("cog = "+bundle.getInt("cog"));
//        				debug("rot = "+bundle.getInt("rot"));
//         				debug("sog = "+bundle.getInt("sog"));
//         				debug("utc_sec = "+bundle.getInt("utc_sec"));
         				
        				tvMsgId.setText(bundle.getInt("msgid")+"");//����ID	
        				tvRelayFlag.setText(bundle.getInt("repeat")+"");	//ת��ָʾ��
        				tvUserId.setText(bundle.getLong("userid")+"");		//�û�ID
        				tvNavStatus.setText(bundle.getInt("nav_status")+"");	//����״̬
        				tvlatitude.setText(latitudeDu+"��"+latitudeFen+"��");		//γ��		
        				tvlongitude.setText(longitudeDu+"��"+longitudeFen+"��");	//����
        				tvCourse.setText(new BigDecimal((double)bundle.getInt("cog")/10).setScale(7,   BigDecimal.ROUND_HALF_UP).doubleValue()+"");		//����
        				tvRotais.setText(bundle.getInt("rot")+"");
        				tvSog.setText(new BigDecimal((double)bundle.getInt("sog")/10).setScale(7,   BigDecimal.ROUND_HALF_UP).doubleValue()+"");
        				tvCog.setText(new BigDecimal((double)bundle.getInt("cog")/10).setScale(7,   BigDecimal.ROUND_HALF_UP).doubleValue()+"");
        				tvTimestamp.setText(bundle.getInt("utc_sec")+"");	//ʱ���
        				
        				break; 
        			case 5:
        				break;
        			case 123:
        				Toast.makeText(getApplicationContext(), "AES��������", Toast.LENGTH_SHORT).show(); 	
        				break;
        			case 200:
        				Toast.makeText(getApplicationContext(), "�������ӳɹ�", Toast.LENGTH_SHORT).show();
        				break;
        			case 201:
        				Toast.makeText(getApplicationContext(), "��������ʧ��", Toast.LENGTH_SHORT).show();
        				break;
        		}
        	}    
        };    
        
        
		
		ipEditText = (EditText)findViewById(R.id.editText1);
		aesEditText = (EditText)findViewById(R.id.editText2);
		button = (Button)findViewById(R.id.button1);
		button01 = (Button)findViewById(R.id.Button01);
		
		if(machineStatus == 2){
			button.setClickable(false);
		}
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String inIp = ipEditText.getText().toString();
				debug("ip = "+inIp);
				tcpClient.init(inIp, mHandler);
//				tcpClient.init("192.168.3.101", mHandler);
				if(machineStatus == 1)	//server
				{
					tcpClient.intTcpServer(tcpServer);	
				}				
				tcpClient.start();
				button.setClickable(false);
				
				
				
				
//				if(machineStatus == 1)	//server
//				{
//					String inIp = ipEditText.getText().toString();
//					debug("ip = "+inIp);
//					tcpClient.init(inIp, mHandler);
////					tcpClient.init("192.168.3.101", mHandler);
//					tcpClient.intTcpServer(tcpServer);	
//					tcpClient.start();
//					button.setClickable(false);
//				}
			}
		});
		button01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String aes = aesEditText.getText().toString(); 
//				if(machineStatus == 2)	//client
//					tcpClient.setAESsectury(aes);
//				else if(machineStatus == 1)
//					tcpServer.setAESsectury(aes); //server
				//�����ڰ������º����������AES���롣����Ϊ�ı����������AES����
				tcpServer.setAESsectury(aes); //server
				
				Toast.makeText(getApplicationContext(), "����AES����ɹ�\r\n����Ϊ"+aes, Toast.LENGTH_SHORT).show();
			}
		});  
		
  
		if(machineStatus == 1)
		{
			Log.i("main", "machineStatus = 1");
			tcpServer = new TcpServer();
			//�����ʼ����һ��AES����
			tcpServer.setAESsectury("123456"); //server
			tcpServer.start();
		}
		
		tcpClient = new TcpClient();
//		tcpClient.init("172.23.159.1", mHandler);
////		tcpClient.init("192.168.3.101", mHandler);
//		if(machineStatus == 1)	//server 
//		{
//			tcpClient.intTcpServer(tcpServer);	
//		}				
//		tcpClient.start();
		
		 

	} 
}
