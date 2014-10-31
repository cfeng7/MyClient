package com.example.myclient;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import clientactive.GetMyCommentMessage;
import clientactive.GetMyStatusMessage;
import clientactive.LoginToServer;
import clientactive.PostCommentMsg;
import clientactive.Registerserver;
import clientactive.RequireNotif;

import com.example.myclientmodel.LogInfo;
import com.example.myclientmodel.ResultInfo;
import com.example.myclientmodel.StatusMessage;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for log in interface, contains the button for register
 ***********************************************************************/
public class MainActivity extends Activity {
    private boolean logpermit=false;
    private String backmessage;
    public Context mcontext;
	public static long clientId=5;
	public static String urlpot="http://10.164.133.118:8080/";
	private int k=1;
//	public MyNotifHandler myNotifHandler;
	private TextView testbox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText useremail=(EditText)findViewById(R.id.editnickname);
		final EditText userpassword=(EditText)findViewById(R.id.editText2);
		//testbox=(TextView)findViewById(R.id.textViewtest1);
		Button login=(Button)findViewById(R.id.getcomment);
		TextView register=(TextView)findViewById(R.id.button4);
		Intent nextpage = getIntent();
		if (nextpage!=null){
			
			try {
				String e=(String) nextpage.getExtras().get("email");
				String p=(String) nextpage.getExtras().get("password");
				useremail.setText(e);
				userpassword.setText(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		//testbox.setText("2");
	//	myNotifHandler=new MyNotifHandler();
		//new Thread(new MySendThread1()).start();//new thread
		
		mcontext = getApplicationContext(); 

		
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {							
				Intent nextpage=new Intent();
				nextpage.setClass(MainActivity.this, Registerwindow.class);
				startActivity(nextpage);
			}
		});
		
		
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {							
				String e=useremail.getText().toString();
				String p=userpassword.getText().toString();
				
				LoginToServer mylog=new LoginToServer(e,p);
				ResultInfo loginresult=mylog.loginserver();
				//GetLocation s=new GetLocation(mcontext);
				//s.openGPSSettings();
				//s.openGPSSettings();
				//System.out.println(GetLocation.lati);
				//System.out.println(GetLocation.longi);
				
				//GetMyCommentMessage gcm=new GetMyCommentMessage(123121);
				//gcm.cmtfromserver();

	            
				
				
				
				
				Date   curDate   =   new   Date(System.currentTimeMillis());
				Timestamp ts = new Timestamp(curDate.getTime());//get current time
				System.out.println(ts);
				
				logpermit=loginresult.isFlag();
				backmessage=loginresult.getMessage();
				//long backid;
//				try{clientId=Long.parseLong(backmessage); }
//				catch(Exception e1){
//					return;
//				}
			    System.out.println(logpermit);
			//	System.out.println(clientId);
				if(logpermit=true){
				Intent nextpage=new Intent();
				nextpage.setClass(MainActivity.this, MainTab.class);
//				try{clientId=Long.parseLong(backmessage); }
//				catch(Exception e1){
//					return;
//				}
				startActivity(nextpage);
			    System.out.println(MainActivity.clientId);
				}
				else{
					
					new AlertDialog.Builder(MainActivity.this).setMessage("Invalid email or password"+"\n"+"\n"+"Please login again").setPositiveButton("Close", null).show();  
				}
			}
		});
		

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

	
}
