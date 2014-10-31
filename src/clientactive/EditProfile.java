package clientactive;
/***********************************************************************
 * Module:  PostStatusMsg.java
 * Author:  Cong Feng
 * Purpose: 
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
import com.example.myclientmodel.Profile;
import com.example.myclientmodel.ResultInfo;
import com.example.myclientmodel.StatusMessage;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class EditProfile {
private Profile profile;
//private String url="http://10.164.130.227:8080/Struts2_Hello_World/editprofile";
private String url=MainActivity.urlpot+"Struts2_Hello_World/editprofile";
private JSONObject backjson;//JSONObject sent back from the server (should be ResultInfo )
public EditProfile (String nickname, int age, String gender, String selfDiscription, int visibility){
	this.profile=new Profile(nickname,age,gender,selfDiscription,visibility);
}
/**
 * 
 * @return 
 */
public ResultInfo postProfile(){//post the message and get the resultinfo class
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
	     JSONObject profilejson=new JSONObject();
	     try {
	    	 System.out.println(MainActivity.clientId);
	    	 profilejson.put("uid", MainActivity.clientId);
	    	 profilejson.put("profile", this.profile.toJson());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   JsonToServer mysendsmsg=new JsonToServer(this.url,profilejson);
	   //System.out.println(profilejson);
	   this.backjson=mysendsmsg.sendtoserver();
	   ResultInfo serverresult=new ResultInfo(backjson);
	return serverresult;
}

}