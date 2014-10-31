package clientactive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myclientmodel.StatusMessage;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for sending request to server to get new message if 
 * this message is related to user
 ***********************************************************************/
public class RequireNotif {
public static long clientId=121212;
private String url="http://www.google.com";
private JSONArray resultjarray;
public RequireNotif() {
	super();
}
public void requestnotif(){
JSONObject json=new JSONObject();
try {
	json.put("clientId", this.clientId);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 this.resultjarray=(new JsonFromServer(this.url)).getarrayserver(json);

}

public List<StatusMessage> arraytosmsglist(){
List<StatusMessage> backsmsglist=new ArrayList<StatusMessage>();
if (this.resultjarray!=null){
for (int i=0;i<=this.resultjarray.length();i++){
	try {
		JSONObject jtemp=(JSONObject) this.resultjarray.get(i);
		JSONObject jtemploca=jtemp.getJSONObject("location");
		StatusMessage smsgtemp=new StatusMessage(jtemp,jtemploca);
		backsmsglist.add(smsgtemp);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
else{
	//System.out.println("empty array");
}
	return backsmsglist;
	
}

}
