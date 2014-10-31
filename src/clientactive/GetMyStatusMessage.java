/***********************************************************************
 * Module:  GetMyStatusMessage.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for getting my status message list from the server 
 ***********************************************************************/
package clientactive;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myclient.MainActivity;
import com.example.myclientmodel.StatusMessage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is similar with the GetAllStatusMessage class, but just get the message
 * posted by users. json request is sent and wait for a jsonarray contains all the message.
 ***********************************************************************/
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GetMyStatusMessage {
//private String urlback="http://10.164.130.227:8080/Struts2_Hello_World/allstatus";
private String urlback=MainActivity.urlpot+"Struts2_Hello_World/allstatus";
private JSONArray resultarray;
	public GetMyStatusMessage(){
		
	}
	/**
	 * this method is mainly for send my clientId to server and get the message list back
	 */
	public void severtosmsg(){
		   
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
	     JSONObject myuserId=new JSONObject();
	     try {
			myuserId.put("uid", MainActivity.clientId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// put clientId in JSONObject for sending
		   JsonFromServer mygetsmsg=new JsonFromServer(this.urlback);
		   //System.out.println("mid");
		  // System.out.println(this.resultarray.length());
		   this.resultarray=mygetsmsg.getarrayserver(myuserId);//call the connect method in JsonFromServer class
	}
	/**
	 * this method is mainly for decouple the jsonarray to message list for the showing
	 * @return smsglistget is a status message list contains all the message sent by the users
	 * @throws ParseException
	 */
	public List<StatusMessage> jsontosmsglist() throws ParseException {
		   List<StatusMessage> smsglistget=new ArrayList<StatusMessage>();
		   if(this.resultarray!=null){
		   try{
		    for(int i = 0; i<this.resultarray.length(); i++) {  
	          JSONObject jsonObject = (JSONObject) resultarray.get(i);
	          JSONObject msgjson=jsonObject.getJSONObject("status");
					JSONObject locjson= msgjson.getJSONObject("location");
					StatusMessage statusmsg=new StatusMessage(msgjson,locjson);			            
			           // Log.v("MainActivity", String.valueOf(sid));
			          //  Log.v("MainActivity", name);
			           // Log.v("MainActivity", String.valueOf(timelength)); 
			            smsglistget.add(statusmsg);
	   }
		    }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1234");
		    }
	}   
		    
		    return smsglistget;
	 }
	
public List<String> jsontonickname(){
	List<String> nicknamelist=new ArrayList<String>();
	if(this.resultarray!=null){
	for (int i=0;i<this.resultarray.length();i++){
		try {
			JSONObject jsonObject=(JSONObject) resultarray.get(i);
			nicknamelist.add(jsonObject.getString("nickname"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	else{
		System.out.println("empty");
	}
	return nicknamelist;
	
}
}
