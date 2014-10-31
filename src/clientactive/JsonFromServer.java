/***********************************************************************
 * Module:  JsonFromServer.java
 * Author:  Cong Feng
 * Purpose: This class is mainly connect the server to send a request and get the needed jsonarray
 ***********************************************************************/
package clientactive;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JsonFromServer {

	
	private String url;
	private JSONArray jsonArray;
	public JsonFromServer(String url){
	  this.url=url;
	}
	
	   /**
	    * This method is mainly for sending a request to server and get the JSONArray from it  
	    * @param sendjson contains the clientId so the server can recognize the user and send back correct information
	    * @return jsonArray sent back from server 
	    */
	 public JSONArray getarrayserver(JSONObject sendjson){
		 HttpClient myhclient = new DefaultHttpClient(); 
		// System.out.println(sendjson);
		// System.out.println("call this method");
        // HttpPost myhpost;
		 HttpPost myhpost = new HttpPost(url); 
		// System.out.println(this.url);
         myhclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); 
         myhclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
         System.out.println(this.url);
         try { 
        	 myhpost.setEntity(new StringEntity(sendjson.toString(),"UTF-8"));
             HttpResponse response = myhclient.execute(myhpost);  
            // System.out.println(this.url);
             System.out.println(response.getStatusLine().getStatusCode());
             if (response.getStatusLine().getStatusCode() == 200) { //200表示请求成功    
                 HttpEntity entity = response.getEntity();    
                 if (entity != null) {    
                     String out = EntityUtils.toString(entity, "UTF-8");                          
                     this.jsonArray = new JSONArray(out);                        
                 }  
             }  
         } catch (UnsupportedEncodingException e) {
    		 // TODO Auto-generated catch block
      
    		 e.printStackTrace();
    		 } catch (JSONException e) {
    		 // TODO Auto-generated catch block
    			 
    		 e.printStackTrace();
    		 } catch (ClientProtocolException e) {
    		 // TODO Auto-generated catch block
    			
    		 e.printStackTrace();
    		 } catch (IOException e) {
    		 // TODO Auto-generated catch block
    		
    		 e.printStackTrace();
    		 }
         return jsonArray;
	 }
  }
