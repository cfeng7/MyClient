
/***********************************************************************
 * Module:  Location.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the Class Location
 ***********************************************************************/

package com.example.myclientmodel;

import org.json.JSONException;
import org.json.JSONObject;



public class Location {
   private double longitude;
   private double latitude;
   private String textDiscription;
   
   /** constructor
    * @param lo longitude got from the GPS
    * @param la latitude got from the GPS
    * @param t  text editted by users describe his position
    * */
   public Location(double lo, double la, String t) {
      longitude = lo;
      latitude = la;
      textDiscription = t;
   }
   
   public Location(Location l) {
	      longitude = l.getLongitude();
	      latitude = l.getLatitude();
	      textDiscription = l.getTextDiscription();
	   }
   
   /**
    * overloaded constructor: construct from json object
    * @param jo is a JSONObject should be sent from the server
 	*/
   public Location(JSONObject jo) {
	   try {
		longitude = jo.getDouble("longitude");
		latitude = jo.getDouble("latitude");
		textDiscription = jo.getString("textDiscription");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

   public double getLongitude() {
      return longitude;
   }
   

   public double getLatitude() {
      return latitude;
   }
   
   
   public String getTextDiscription() {
      return textDiscription;
   }
   
   public void setLongitude(double longitude) {
	this.longitude = longitude;
}

public void setLatitude(double latitude) {
	this.latitude = latitude;
}

public void setTextDiscription(String textDiscription) {
	this.textDiscription = textDiscription;
}

public void update(Location l) {
      longitude = l.getLongitude();
      latitude = l.getLatitude();
      textDiscription = l.getTextDiscription();
   }
/**
 * This class mainly for transform a Location to JSONObject
 * @return locatiojson as the  JSONObject 
 */ 
   public JSONObject toJson(){
	   JSONObject locationjson=new JSONObject();
	   try {
		locationjson.put("latitude", this.latitude);
		locationjson.put("longitude", this.longitude);
		locationjson.put("textDiscription", this.textDiscription);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return locationjson;
   }

}