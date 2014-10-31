/***********************************************************************
 * Module:  LoginToServer.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for sending the log in information to server
 ***********************************************************************/
package clientactive;


import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.example.myclient.MainActivity;
import com.example.myclientmodel.LogInfo;
import com.example.myclientmodel.ResultInfo;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for sending a jsonobject contains email and password to 
 * server for logging in.
 ***********************************************************************/
//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class LoginToServer {
	private LogInfo loginfo;
	//private String url="http://10.164.133.118:8080/Struts2_Hello_World/login";
	private String url=MainActivity.urlpot+"Struts2_Hello_World/login";
	//private String url="http://www.google.com";
	private JSONObject backjson;//JSONObject sent back from the server (should be ResultInfo )
	public LoginToServer(String email, String password){
		this.loginfo=new LogInfo(email,password);
		
	}
	/**
	 * This method is mainly for send the login information to server 
	 * @return serverresult feed back from the server
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public ResultInfo loginserver(){//post the message and get the resultinfo class
	       JSONObject loginjson=new JSONObject();
	       System.out.println(loginfo.getEmail());
	     loginjson=this.loginfo.toJson();//put the commentmsg to json
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
		 JsonToServer mysendlogin=new JsonToServer(this.url,loginjson);// new jsontosever object
		this.backjson=mysendlogin.sendtoserver();//send the log in to server and get the backjson jobject
		  ResultInfo serverresult=new ResultInfo(backjson);//put backjson to ResultInfo class form
		return serverresult;//return the ResultInfo class
	}
}
