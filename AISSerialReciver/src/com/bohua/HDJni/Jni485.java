package com.bohua.HDJni;

public class Jni485 {

	public static int init_state = -1;
	public static String read()
	{ 
		return jniRead485();
	}
	public static int write(int str[] ,int len)
	{
		if(init_state == -1) return init_state;
		return jniWrite485(str,len);
	}
	public static int[] ret()
	{ 
		return xxxx();
	}
	public static int retFlag()
	{
		return readflag();
	}
	public static int retReadLength()
	{
		return readlength();
	}
	public static int init(String baud)
	{
		int _baud = 0;
		if(baud.equals("0"))				_baud= 0;
		else if(baud.equals("300"))			_baud= 7;
		else if(baud.equals("600"))			_baud= 10;
		else if(baud.equals("1200"))		_baud= 11; 
		else if(baud.equals("1800"))		_baud= 12;
		else if(baud.equals("2400"))		_baud= 13;
		else if(baud.equals("4800"))		_baud= 14;
		else if(baud.equals("9600"))		_baud= 0000015;
		else if(baud.equals("19200"))		_baud= 0000016;
		else if(baud.equals("38400"))		_baud= 0000017/*15*/;
		else if(baud.equals("57600"))		_baud= 0010001;
		else if(baud.equals("115200"))		_baud= 0010002;
		else if(baud.equals("576000"))		_baud= 0010006;
		else if(baud.equals("1152000"))		_baud= 0010011;
		else	
			return init_state = -1;
		return init_state = jniSet485Para(_baud);
	}
	 
	 
	/*初始化485接口，参数在jni文件中写死*/
	private native static int jniSet485Para(int baud);
	/*读485数据*/
	private native static String jniRead485();
    /*写485数据*/
	private native static int jniWrite485(int data[],int len);
    
	private native static int[] xxxx();
	
	private native static int readflag();
	
	private native static int readlength();
	
    static {
        System.loadLibrary("uart");
    }
}
