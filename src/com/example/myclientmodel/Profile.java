
/***********************************************************************
 * Module:  Profile.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the Class Profile for the transportation of user profile
 ***********************************************************************/

package com.example.myclientmodel;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;


public class Profile {
   private String nickname;
   private int age;
   private String gender;
   private String selfDiscription;
   private int visibility;
    
   /** constructor
    * @param nickname nickname of the user
    * @param gender 
    * @param selfDiscription 
    * @param visibility if my profile could be seen by other user
    *  */
   public Profile(String nickname, int age, String gender, String selfDiscription, int visibility) {
      this.nickname = nickname;
      this.gender = gender;
      this.age = age;
      this.selfDiscription = selfDiscription;
      this.visibility = visibility;
   }
   
   public Profile(Profile p){
	   this.nickname = p.getNickName();
	   this.age = p.getAge();
	   this.gender = p.getGender();
	   this.selfDiscription = p.getSelfDiscription();
	   this.visibility = p.getVisibility();
   }
   /**
    * overloaded constructor: construct from json object
    * @param jo is a JSONObject should be got from server
 	*/
   public Profile(JSONObject jo) {
	   try {
		nickname = jo.getString("nickname");
		age = jo.getInt("age");
		gender = jo.getString("gender");
		selfDiscription = jo.getString("selfDiscription");
		visibility = jo.getInt("visibility");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * This class mainly for transform a Profile to JSONObject
    * @return profilejson as the  JSONObject 
    */ 
   public JSONObject toJson(){
	  JSONObject profilejson=new JSONObject();
	  try {
		profilejson.put("age",this.age);
		  profilejson.put("gender",this.gender);
		  profilejson.put("nickname",this.nickname);
		  profilejson.put("selfDiscription",this.selfDiscription);
		  profilejson.put("visibility",this.visibility);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
      return profilejson;
   }
   
   public String getNickName() {
      return nickname;
   }
   
   public int getAge() {
	   return age;
   }
   
   public String getGender() {
      return gender;
   }

   public String getSelfDiscription() {
      return selfDiscription;
   }

   public int getVisibility() {
      return visibility;
   }
   
public void setNickname(String nickname) {
	this.nickname = nickname;
}

public void setAge(int age) {
	this.age = age;
}

public void setGender(String gender) {
	this.gender = gender;
}

public void setSelfDiscription(String selfDiscription) {
	this.selfDiscription = selfDiscription;
}

public void setVisibility(int visibility) {
	this.visibility = visibility;
}

}
