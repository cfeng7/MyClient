package clientactive;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.example.myclient.MainActivity;
import com.example.myclient.MyGoogleMap;
import com.example.myclientmodel.Location;
import com.example.myclientmodel.StatusMessage;

public class GetNotify {
//private String urlback="http://www.google.com";
private String urlback=MainActivity.urlpot+"Struts2_Hello_World/..";
private JSONArray resultarray;
	public GetNotify(){
		
	}
	/**
	 * this method is mainly for send my clientId to server and get the message list back
	 */
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
	     //get longitude and latitude, put into location, to json
//	     this.latitude=MyGoogleMap.getCurrentLatitude();
//	     this.longitude=MyGoogleMap.getCurrentLongitude();
//	     JSONObject mylocajson=(new Location(this.longitude,this.latitude,"location").toJson());
	 
	     try {
	    //client id and location in a jsonobject named myuserId
			myuserId.put("uid", MainActivity.clientId);
//			myuserId.put("location", mylocajson);
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
			   int length=this.resultarray.length();
			   System.out.println(length);
		   try{
		    for(int i = 0; i<length; i++) { 
		    	System.out.println(i);
	          JSONObject jsonObject = (JSONObject) resultarray.get(i);
	          System.out.println(jsonObject);
	          JSONObject msgjson=jsonObject.getJSONObject("status");
					JSONObject locjson= msgjson.getJSONObject("locjson");
					StatusMessage statusmsg=new StatusMessage(msgjson,locjson);			            
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
