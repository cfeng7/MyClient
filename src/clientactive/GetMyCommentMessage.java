package clientactive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.widget.ListView;

import com.example.myclient.MainActivity;
import com.example.myclient.R;
import com.example.myclientmodel.CommentMessage;
import com.example.myclientmodel.StatusMessage;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for sending a request and looking for all the comments from a message
 ***********************************************************************/
@SuppressLint("NewApi")
public class GetMyCommentMessage {
//private String url="http://www.yahoo.com";
private String url=MainActivity.urlpot+"Struts2_Hello_World/getallcomments";
private JSONArray resultarray;
private long mothermsgid;
public GetMyCommentMessage(long mothermsgid){//send the status message id to the constructor, the server get this id and back correct comments list
	this.mothermsgid=mothermsgid;
}
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public JSONArray cmtfromserver(){
	
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
	     
	JSONObject sendjson=new JSONObject();
	try {
		sendjson.put("uid", MainActivity.clientId);
		sendjson.put("motherid", this.mothermsgid);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	JsonFromServer getcmtlist=new JsonFromServer(this.url);// set new object
	this.resultarray=getcmtlist.getarrayserver(sendjson);
	return resultarray;	
}

public List<CommentMessage> jsontocmtlist(){
	List<CommentMessage> cmtmsglist=new ArrayList<CommentMessage>();
	if (this.resultarray!=null){
	for (int i=0;i<this.resultarray.length();i++){
		try {
	          JSONObject jsonObject = (JSONObject) resultarray.get(i);
	         // System.out.println(jsonObject);
	          JSONObject cmtjson=jsonObject.getJSONObject("comment");
				CommentMessage comment=new CommentMessage(cmtjson);		            
			            cmtmsglist.add(comment);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	return cmtmsglist;
	
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
