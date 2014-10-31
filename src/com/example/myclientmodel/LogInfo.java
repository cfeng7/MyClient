/***********************************************************************
 * Module:  LogInfo.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the Class LogInfo
 ***********************************************************************/

package com.example.myclientmodel;

import org.json.JSONException;
import org.json.JSONObject;


public class LogInfo {

   private String email;
   private String password;
   
   /** constructor
    * @param email for log in and also in my profile
    * @param password should be set by users
    *  */
   public LogInfo(String email, String password) {
      this.email = email;
      this.password = password;
   }
   public LogInfo(LogInfo li){
	   this.email = li.getEmail();
	   this.password = li.getPassword();
   }
   
   /**
    * overloaded constructor: construct from json object
    * @param jo is a JSONObeject should be get from server
 	*/
   public LogInfo(JSONObject jo){
	   try {
		email = jo.getString("email");
		password = jo.getString("password");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * This class mainly for transform a LogInfo to JSONObject
    * @return logInfojson as the  JSONObject 
    */  
   public JSONObject toJson(){
	   JSONObject logInfojson=new JSONObject();
	   try {
		logInfojson.put("email",this.email);
		logInfojson.put("password",this.password);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	   return logInfojson;

   }

   public String getEmail() {
      return email;
   }
   
   public String getPassword() {
      return password;
   }
   
public void setEmail(String email) {
	this.email = email;
}
public void setPassword(String password) {
	this.password = password;
}

}