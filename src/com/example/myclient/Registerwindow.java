package com.example.myclient;

import org.json.JSONObject;

import clientactive.Registerserver;
import clientactive.RequireNotif;

import com.example.myclientmodel.LogInfo;
import com.example.myclientmodel.Profile;
import com.example.myclientmodel.ResultInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for new users to register
 ***********************************************************************/
public class Registerwindow extends Activity {
	private static final String[] m={"Male","Famle"};
	private static final String[] n={"Yes, others can see me","No, I want to keep invisible"};
	private EditText myemail;
	private EditText mypassword;
	private ArrayAdapter<String> spadapter; 
	private ArrayAdapter<String> vbadapter; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registerwindow);
		final EditText mynickname=(EditText) findViewById(R.id.editnickname);
		final EditText myage=(EditText) findViewById(R.id.editText2); 
		//final EditText myselfd=(EditText) findViewById(R.id.editText3); 
		myemail=(EditText) findViewById(R.id.editText4); 
		mypassword=(EditText) findViewById(R.id.editText5);
		Button submit=(Button) findViewById(R.id.getcomment);
		TextView buttonback=(TextView) findViewById(R.id.button_regi_to_main);
		final Spinner genderspinner = (Spinner) findViewById(R.id.spinner1_gender); 
		//final Spinner visiblespinner = (Spinner) findViewById(R.id.spinner2);
		spadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        vbadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,n);
        genderspinner.setAdapter(spadapter); //two adapters for two spinner  
       // visiblespinner.setAdapter(vbadapter);
        
        buttonback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent nextpage=new Intent();
				nextpage.setClass(Registerwindow.this, MainActivity.class);
				startActivity(nextpage);
			}
		});
        //new thread
        // unnecessary listener
       genderspinner.setOnItemSelectedListener(new OnItemSelectedListener(){//add listener to gender
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String gder=spadapter.getItem(arg2).toString() ;
				int p=vbadapter.getPosition(gder);
				System.out.println(p);				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}      	
        });  
        
//        visiblespinner.setOnItemSelectedListener(new OnItemSelectedListener(){//add listener to gender
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				String visible=vbadapter.getItem(arg2).toString() ;
//				int q=vbadapter.getPosition(visible);
//				System.out.println(q);				
//			}
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {			
//			}      	
//        });  
        
        
        //genderspinner.setVisibility(View.VISIBLE); 
    	submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String sex="F";
				int age = 0;
				int visib=1;
				String nickname=mynickname.getText().toString();
				
				try{age=Integer.parseInt(myage.getText().toString());}
				catch(Exception e){
		               return;
		              }
				
				if("Male"==genderspinner.getSelectedItem().toString()){
					sex="M";
				}
//				if("Yes, others can see me"==visiblespinner.getSelectedItem().toString()){
//					visib=0;
//				}
				//String selfdp=myselfd.getText().toString();
				String selfdp = "";
				String email=myemail.getText().toString();
				String password=mypassword.getText().toString();
				Registerserver register=new Registerserver(password, nickname, age, email, sex, selfdp, visib);
				if (age>100||age<0){
					Toast.makeText(Registerwindow.this, "Sorry, Please enter valid age ", Toast.LENGTH_LONG).show();	
				}else
				{ if(myemail.getText().toString().indexOf("@")==-1){
					Toast.makeText(Registerwindow.this, "Please enter valid email address", Toast.LENGTH_LONG).show();
				}
				else{
				ResultInfo backresult=register.registerserver();
				System.out.println("register to server");
				if 	(backresult.isFlag()==true){
					Toast.makeText(Registerwindow.this, "Dear "+mynickname.getText().toString()+", Register successfully ", Toast.LENGTH_LONG).show();
					new Thread(new MySendThread2()).start();//start thread, wait 2 sec and back to mainactivity
					int c=0;
					try {
						c=Integer.parseInt(backresult.getMessage());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainActivity.clientId=c;
				}
				else {
					
					Toast.makeText(Registerwindow.this, backresult.getMessage(), Toast.LENGTH_LONG).show();
				}
				}
			}
			}
		
		});
        
        
        
     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registerwindow, menu);
		return true;
	}
	
	
	public class MySendThread2 implements Runnable {
		//public static List<StatusMessage> newsmsg;
		public int p=1;
		private Handler handler;
		public MySendThread2(){
			this.handler = new Handler() {
			    public void handleMessage(Message msg) {
					  super.handleMessage(msg);
					  Bundle b=msg.getData();		  
					  //Toast.makeText(Registerwindow.this, "stupid guy", Toast.LENGTH_LONG).show();
						Intent nextpage=new Intent();
						nextpage.setClass(Registerwindow.this, MainActivity.class);
						nextpage.putExtra("email", myemail.getText().toString());
						nextpage.putExtra("password", mypassword.getText().toString());
						startActivity(nextpage);
			    }
			};
		}
	    @Override
	    public void run() {
	        // TODO Auto-generated method stub   
	            try {
	                Thread.sleep(2000);// 1s

//	                	this.p++;
	                	Message msg = new Message();
//	                	Bundle b=new Bundle();
//	                	b.putInt("pnum", p);
//	                	msg.setData(b);
	                	this.handler.sendMessage(msg);
	                    //handler.sendMessage(msg);
	                	//System.out.println("emptylist");
	                
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	    }
	}

}
