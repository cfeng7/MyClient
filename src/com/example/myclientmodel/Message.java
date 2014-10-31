/***********************************************************************
 * Module:  Message.java
 * Author:  Yang
 * Edited:  Cong Feng
 * Purpose: Defines the father class of all type of message
 ***********************************************************************/

package com.example.myclientmodel;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.json.JSONObject;

/** father class of comment and status */
public abstract class Message {
	
	private long id;
   private String content;
   private Timestamp date;
 
   
   /**
    * 
    * @param id message id without duplication indicates the message
    * @param content the text user wants to send
    * @param date the time when the message is posted
    */
   public Message(long id, String content, Timestamp date) {
      this.id = id;
	  this.content = content;
	 // Timestamp ts= new Timestamp(new Date().getTime());
    //  this.date = ts;
	  this.date=date;
   }
   
   public long getId(){
	   return id;
   }
   

   public String getContent() {
      return content;
   }
   

   public Timestamp getDate() {
      return date;
   }
   
   public JSONObject toJson(){
	   return null;
   }

public void setId(long id) {
	this.id = id;
}

public void setContent(String content) {
	this.content = content;
}

public void setDate(Timestamp date) {
	this.date = date;
}

}