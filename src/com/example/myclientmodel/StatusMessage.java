
/***********************************************************************
 * Module:  StatusMessage.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the Class StatusMessage for the transportation of status
 ***********************************************************************/
package com.example.myclientmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;


public class StatusMessage extends Message {

	private double range;
   public Location location;
   
   
   /** constructor
    * @param r describe the distribution range of this message
    * @param l describe the location of the user when posting this message
    *  */
   public StatusMessage(long id, String content, Timestamp date, double range, Location location) {
		super(id, content, date);
		this.range = range;
		this.location = new Location(location);
	}
   
   public StatusMessage(StatusMessage sm){
	   super(sm.getId(), sm.getContent(), sm.getDate());
	   range = sm.getRange();
	   location = new Location(sm.getLocation());
   }
   /**
    * overloaded constructor: construct from json object
    * @param jo1 is the JSONOject for StatusMessage from server
    * @param jo2 is the JSONOject for Location got from server
 * @throws JSONException 
 	*/
	public StatusMessage(JSONObject jo1, JSONObject jo2) throws JSONException {
		
		super(jo1.getLong("id"), jo1.getString("content"), Timestamp.valueOf(jo1.getString("date")));
		try {
			range = jo1.getLong("range");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		location = new Location(jo2);
	}

   public double getRange() {
      return range;
   }
   

   public Location getLocation() {
      return location;
   }
   
   public void setRange(double range) {
	this.range = range;
}

public void setLocation(Location location) {
	this.location = location;
}
/**
 * This class mainly for transform a StatusMessage to JSONObject
 * @return smsgjson as the  JSONObject 
 */ 
public JSONObject toJson(){
	   JSONObject smsgjson=new JSONObject();
	   JSONObject locjson=location.toJson();
	   try {
		   smsgjson.put("id", this.getId());
		   smsgjson.put("content", this.getContent());
		   smsgjson.put("date", this.getDate().toString());
		   smsgjson.put("range", this.range);
		   smsgjson.put("locjson", locjson);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return smsgjson;
   }

}