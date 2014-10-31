/***********************************************************************
 * Module:  JsonToServer.java
 * Author:  Cong Feng
 * Purpose: This class is mainly for connecting the server and sending the necessary jsonobject, server
 * may return a jsonobject for feedback
 ***********************************************************************/

package clientactive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myclient.MainActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JsonToServer {
	private String url;
	private JSONObject jsonobject;
	private String stringback;
	private JSONObject result = new JSONObject();

	public JsonToServer(String url, JSONObject jsonobject) {
		this.url = url;
		this.jsonobject = jsonobject;
		//System.out.println(url);
	}

	/**
	 * This method is mainly for send the JSONOJECT to server and get the
	 * feedback
	 * 
	 * @return result contains the feedback information from the server
	 */
	public JSONObject sendtoserver() {
		HttpClient myhclient = new DefaultHttpClient();
		HttpPost myhpost = new HttpPost(this.url);
		System.out.println("2");
		 myhclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
		 myhclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4000);
		 try{
			 System.out.println("3");
//		myhpost.setHeader("Content-type","application/json");
//		myhpost.setHeader("Accept","application/json");
		 myhpost.setEntity(new StringEntity(this.jsonobject.toString(),"UTF-8"));		 
		// HttpParams params= new BasicHttpParams();
		// params.setParameter("logInfo",s);
		// myhpost.setParams(params);
		 HttpResponse res= myhclient.execute(myhpost);
		 System.out.println("4");
		 int m = res.getStatusLine().getStatusCode();
		 //System.out.print(jsonobject);
		 System.out.println(m);
		 if (res.getStatusLine().getStatusCode() == 200) {
		 stringback = EntityUtils.toString(res.getEntity());
		 this.result = new JSONObject(stringback);			 
		 } else {
		 System.out.println("fail to connect");
		 }
		 }catch (UnsupportedEncodingException e) {
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


		return this.result;
	}

}
