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
import com.example.myclientmodel.Profile;
import com.example.myclientmodel.StatusMessage;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ViewMyself {
//private String urlback="http://10.164.130.227:8080/Struts2_Hello_World/myprofile";
private String urlback=MainActivity.urlpot+"Struts2_Hello_World/myprofile";
private JSONArray resultarray;
	public ViewMyself(){

	}
	/**
	 *
	 */
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void severtoprofile(){
		   
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
	 * 
	 * @
	 * @
	 */
    public Profile getprofile(){
    	JSONObject jsonprofile= new JSONObject();
    	System.out.println(resultarray);
    	try {
			jsonprofile=(JSONObject) this.resultarray.get(0);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Profile profile=new Profile(jsonprofile);
		return profile;
    	
    }
}
