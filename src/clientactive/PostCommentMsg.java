/***********************************************************************
 * Module:  PostCommentMsg.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for posting a new comment message to server
 ***********************************************************************/
package clientactive;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.example.myclient.MainActivity;
import com.example.myclientmodel.CommentMessage;
import com.example.myclientmodel.ResultInfo;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PostCommentMsg {
	private CommentMessage postcmsg;
	//private String url="http://www.google.com";
	private String url=MainActivity.urlpot+"Struts2_Hello_World/comment";
	private JSONObject backjson;//JSONObject sent back from the server (should be ResultInfo )
	public PostCommentMsg(long id, String content, Timestamp date, long sid){
		this.postcmsg=new CommentMessage(id,content,date,sid);
	}
	/**
	 * this method mainly for post a comment msg to the server 
	 * @return serverresult feedback from the server
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public ResultInfo postMsg(){//post the message and get the resultinfo class
		   JSONObject postcmsgjson=new JSONObject();
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
	        //postcmsgjson=this.postcmsg.toJson();//put the commentmsg to json
	        try {
				postcmsgjson.put("uid", MainActivity.clientId);
				postcmsgjson.put("comment", this.postcmsg.toJson());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   JsonToServer mysendcmsg=new JsonToServer(this.url,postcmsgjson);// new jsontosever object
		   this.backjson=mysendcmsg.sendtoserver();//send the comment msg to server and get the backjson jobject
		   ResultInfo serverresult=new ResultInfo(backjson);//put backjson to ResultInfo class form
		 //  System.out.println("PSMSG");
		return serverresult;//return the ResultInfo class
		
	}
}
