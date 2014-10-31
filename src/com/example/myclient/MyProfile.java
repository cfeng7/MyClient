package com.example.myclient;

import com.example.myclientmodel.Profile;
import com.example.myclientmodel.ResultInfo;

import clientactive.EditProfile;
import clientactive.ViewMyself;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/***********************************************************************
 * @author: Cong Feng
 * Purpose: This class is mainly for editing the user's profile when he has logged in
 ***********************************************************************/
public class MyProfile extends Activity {
	private EditText newnickname;
	private EditText newage;
	private EditText newstatus;
	private Spinner editvisibility;
	private String[] s={"Yes, others can see me","No, I want to keep invisible"};
	private ArrayAdapter<String> sbadapter;
	private Button editprofile;
	private Profile myoldprofile;
	private String mygender="M";
	private Button exitapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		newnickname=(EditText) findViewById(R.id.editnickname);
		newage=(EditText) findViewById(R.id.editage);
		newstatus=(EditText) findViewById(R.id.editselfdis);
		editprofile=(Button) findViewById(R.id.button_refreshmymsg_msglist);
		editvisibility = (Spinner) findViewById(R.id.editvisiblespinner1);
		exitapp=(Button) findViewById(R.id.button_exit);
		sbadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,s);
		editvisibility.setAdapter(sbadapter);
		try {
			ViewMyself viewme=new ViewMyself();
			viewme.severtoprofile();
			 this.myoldprofile=viewme.getprofile();
			 this.mygender=myoldprofile.getGender();
			newnickname.setText(myoldprofile.getNickName());
			newage.setText(Integer.toString(myoldprofile.getAge()));
			newstatus.setText(myoldprofile.getSelfDiscription());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		exitapp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		
		
		
		editprofile.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				int age = 0;int visibility=1;
				String nickname=newnickname.getText().toString();
				String status=newstatus.getText().toString();
				
				try {
					age=Integer.parseInt((newage.getText().toString()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				
				if("Yes, others can see me"==editvisibility.getSelectedItem().toString()){
					visibility=0;
				}
				
				if (age<0||age>100){
					Toast.makeText(MyProfile.this, "Sorry, Please enter valid age ", Toast.LENGTH_LONG).show();	
				}
				else{
					EditProfile editprofile=new EditProfile(nickname, age,mygender, status,visibility);
					
					ResultInfo result=editprofile.postProfile();
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_profile, menu);
		return true;
	}

}
