/***********************************************************************
 * Module:  Registerserver.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for new users to send their register information to server
 ***********************************************************************/
package clientactive;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.example.myclient.MainActivity;
import com.example.myclientmodel.LogInfo;
import com.example.myclientmodel.Profile;
import com.example.myclientmodel.ResultInfo;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for sending all the elements related to registration to 
 * the server
 ***********************************************************************/
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Registerserver {
	private LogInfo loginfo;
	private Profile newProfile;
	//private String url="http://10.164.130.227:8080/Struts2_Hello_World/register";
	private String url=MainActivity.urlpot+"Struts2_Hello_World/register";
	private JSONObject backjson;//JSONObject sent back from the server (should be ResultInfo )
	public Registerserver(String password, String nickname, int age, String email, String gender, String selfDiscription, int visibility){
		this.loginfo=new LogInfo(email,password);
		this.newProfile=new Profile(nickname,age,gender,selfDiscription,visibility);
	}
	/**
	 * this method is mainly for sending the register information to the server
	 * @return serverresult contains feedback information
	 */
	public ResultInfo registerserver(){//post the message and get the resultinfo class
		   JSONObject registerjson=new JSONObject();
		   JSONObject loginfojson=new JSONObject();
		   JSONObject profilejson=new JSONObject();
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
	        loginfojson=this.loginfo.toJson();//put the two classes to jsonobjects and make the latter in another jsonobject 
	        profilejson=this.newProfile.toJson();
	        try {
				registerjson.put("logInfo", loginfojson);
				registerjson.put("profile", profilejson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   JsonToServer mysendregister=new JsonToServer(this.url,registerjson);// new jsontosever object
		   this.backjson=mysendregister.sendtoserver();//send the register to server and get the backjson jobject
		   ResultInfo serverresult=new ResultInfo(backjson);//put backjson to ResultInfo class form
		return serverresult;//return the ResultInfo class
	}
}
