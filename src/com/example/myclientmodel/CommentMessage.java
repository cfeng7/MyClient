

/***********************************************************************
 * Module:  Comment.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the Class Comment that for the transportation of comment 
 ***********************************************************************/

package com.example.myclientmodel;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;


public class CommentMessage extends Message {

	private long sid;
   
   /** constructor
    * @param sid an id for each commentmessage without duplication
    *  */
	public CommentMessage(long id, String content, Timestamp date, long sid) {
		super(id, content, date);
		this.sid = sid;
	}
	
	public CommentMessage(CommentMessage cm){
		super(cm.getId(), cm.getContent(), cm.getDate());
		this.sid = cm.getSid();
		
	}
	
	public void setSid(long sid) {
		this.sid = sid;
	}

	/**
	 * overloaded constructor: construct from json object
	 * @param jo
	 * @throws JSONException 
	 */
	public CommentMessage(JSONObject jo) throws JSONException {
		super(jo.getLong("id"), jo.getString("content"),Timestamp.valueOf(jo.getString("date")));
		try {
			sid = jo.getLong("sid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
   /**
    * 
    * @return
    */
   public long getSid() {
	      // TODO: implement
	      return sid;
	   }
   /**
    * This class mainly for transform a CommentMessage to JSONObject 
    */
   public JSONObject toJson(){
	   JSONObject cmtjson=new JSONObject();
	   try {
		   cmtjson.put("id", this.getId());
		   cmtjson.put("sid", this.sid);
		   cmtjson.put("content", this.getContent());
		   cmtjson.put("date", this.getDate().toString());
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return cmtjson;
   }

}