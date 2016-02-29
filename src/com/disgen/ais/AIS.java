package com.disgen.ais;

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;
import android.util.Log;

public class AIS {
	
//	public String telegramId;		/*电文ID*/
//	public String relayFlag;		/*转发指示器*/
//	public String userId;			/*用户ID*/
//	public String navigationStatus;	/*导航状态*/
//	public String latitude;			/*纬度*/
//	public String longitude;		/*经度*/
//	public String course;			/*航向*/
//	public String rotais;
//	public String sog;
//	public String cog;
//	public String timestamp;		/*时间戳*/
//	
	int result;
	List<String> msgString;
	public int msgType;
	public Message1 msg1;
	public Message5 msg5;
	
	public AIS(){
		msgString = new ArrayList<String>();
	}
	private void debug(String s){
		Log.i("AIS", s);
	}
	private void warring(String s){
		Log.w("AIS", s);
	}
	public void parse(String str){
		Vdm vdm_message = new Vdm();
		if(str.charAt(7)=='1'){
			msg1 = new Message1();
			try {
				debug("msg1");
	//			 result = vdm_message.add("!AIVDM,1,1,,B,19NS7Sp02wo?HETKA2K6mUM20<L=,0*27\r\n");
				 result = vdm_message.add(str);
				 junit.framework.Assert.assertEquals( "vdm add failed", 0, result );
				 msg1.parse( vdm_message.sixbit() );
				 System.out.println(msg1.nav_status());
				 System.out.println(msg1.rot());
				 System.out.println(msg1.sog());
				 System.out.println(msg1.pos_acc());
				 System.out.println(msg1.pos.latitude());
				 System.out.println(msg1.pos.longitude());
				 System.out.println(msg1.cog());
				 System.out.println(msg1.true_heading());
				 System.out.println(msg1.utc_sec());
				 System.out.println(msg1.regional());
				 System.out.println(msg1.spare());
				 System.out.println(msg1.raim());
				 System.out.println(msg1.sync_state());
				 System.out.println(msg1.slot_timeout());
				 System.out.println(msg1.sub_message());
				 msgType = 1;
			} catch (Exception e) {
				junit.framework.Assert.fail(e.getMessage());
				warring(e.getMessage());
			}catch (AssertionFailedError e){
				warring("AssertionFailedError msg1");
			}
		}
		else if(str.charAt(7)=='2'&&str.charAt(9)=='1'){
			msgString.add(str);
		}
		else if(str.charAt(7)=='2'&&str.charAt(9)=='2'){
			msgString.add(str);
			msg5 = new Message5();
			try {
				debug("msg5");
				 result = vdm_message.add(msgString.get(0));
				 junit.framework.Assert.assertEquals( "vdm add failed", 1, result );

				 result = vdm_message.add(msgString.get(1));
				 junit.framework.Assert.assertEquals( "vdm add failed", 0, result );
				 
				 msg5.parse( vdm_message.sixbit() );
				 System.out.println(msg5.msgid());
				 System.out.println(msg5.repeat());
				 System.out.println(msg5.userid());
				 System.out.println(msg5.version());
				 System.out.println(msg5.imo());
				 System.out.println(msg5.callsign());
				 System.out.println(msg5.name());
				 System.out.println(msg5.ship_type());
				 System.out.println(msg5.dim_bow());
				 System.out.println(msg5.dim_stern());
				 System.out.println(msg5.dim_starboard());
				 System.out.println(msg5.dim_port());
				 System.out.println(msg5.pos_type());
				 System.out.println(msg5.eta());
				 System.out.println(msg5.draught());
				 System.out.println(msg5.dest());
				 System.out.println(msg5.dte());
				 System.out.println(msg5.spare());
				 msgString.clear();
				 msgType = 5;
			} catch (Exception e) {
				junit.framework.Assert.fail(e.getMessage());
				warring(e.getMessage());
			} catch (AssertionFailedError e){
				warring("AssertionFailedError msg5");
			}
		}
	}
	
	
	
}
