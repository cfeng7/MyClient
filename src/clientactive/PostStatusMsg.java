package clientactive;
/***********************************************************************
 * Module:  PostStatusMsg.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for posting a new status message to server
 ***********************************************************************/
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.example.myclient.MainActivity;
import com.example.myclientmodel.Location;
import com.example.myclientmodel.ResultInfo;
import com.example.myclientmodel.StatusMessage;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PostStatusMsg {
private StatusMessage postmsg;
//private String url="http://10.164.133.118:8080/Struts2_Hello_World/post";
private String url=MainActivity.urlpot+"Struts2_Hello_World/post";
private JSONObject backjson;//JSONObject sent back from the server (should be ResultInfo )
public PostStatusMsg(long id, String content, Timestamp date, double range, Location location){
	this.postmsg=new StatusMessage(id,content,date,range,location);
}
/**
 * this method is mainly for posting a Status Message edited by user to the server 
 * @return serverresult  contains feedback from the server
 */
public ResultInfo postMsg(){//post the message and get the resultinfo class
	  // JSONObject statusjson=new JSONObject();
	   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
       .detectDiskReads()  
       .detectDiskWrites()  
       .detectNetwork()   // or .detectAll() for all detectable problems  
       .penaltyLog()  
       .build());  
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
       .detectLeakedSqlLiteObjects()  
       .detectLeakedClosableObjects()  
       .penaltyLog()  
       .penaltyDeath()  
       .build()); 
	     JSONObject statusjson=new JSONObject();
	     try {
	    	 System.out.println(MainActivity.clientId);
	    	 statusjson.put("uid", MainActivity.clientId);
	    	 statusjson.put("status", this.postmsg.toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   JsonToServer mysendsmsg=new JsonToServer(this.url,statusjson);
	   System.out.println("1");
	   this.backjson=mysendsmsg.sendtoserver();
	   ResultInfo serverresult=new ResultInfo(backjson);
	return serverresult;
}

}
