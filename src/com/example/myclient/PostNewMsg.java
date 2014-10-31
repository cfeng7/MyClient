package com.example.myclient;

import java.sql.Timestamp;
import java.util.Date;

import clientactive.PostStatusMsg;

import com.example.myclientmodel.Location;
import com.example.myclientmodel.ResultInfo;
import com.example.myclientmodel.StatusMessage;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for posting the new messages to server
 ***********************************************************************/
public class PostNewMsg extends Activity {
	private EditText msgcontent;
	private EditText msglocatext;
	private Button msggetloca;
	private Button msgsubmit;
	private TextView latitude;
	private TextView longitude;
    private double currentLongitude;
	private double currentLatitude;
	private ResultInfo result;
	private String[] rg={"50","100","200","300","500","700","1000","1500","2000","2500","3000","5000","10000","50000"};
	private ArrayAdapter radapter;
	private Spinner s_rg;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_new_msg);
		msgcontent=(EditText) findViewById(R.id.editText_post_content);
		msglocatext=(EditText) findViewById(R.id.editText_post_locationtext);
		//msggetloca=(Button) findViewById(R.id.button_post_getloca);
		msgsubmit=(Button) findViewById(R.id.button_post_submit);
		latitude=(TextView) findViewById(R.id.text_post_latitude);
		longitude=(TextView) findViewById(R.id.text_post_longitude);
		s_rg=(Spinner) findViewById(R.id.spinner_range_post);
		radapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rg);
		s_rg.setAdapter(radapter);
		msgsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Timestamp ts= new Timestamp(new Date().getTime());double range=0;
				//Location msglocation=new Location(11,11,msglocatext.getText().toString());
				currentLongitude=MyGoogleMap.getCurrentLongitude();
				currentLatitude=MyGoogleMap.getCurrentLatitude();
				latitude.setText(Double.toString(currentLatitude));
				longitude.setText(Double.toString(currentLongitude));
				Location msglocation=new Location(currentLongitude,currentLatitude,msglocatext.getText().toString());
				try {
					range=Double.parseDouble(s_rg.getSelectedItem().toString());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (currentLatitude == 0 && currentLongitude == 0) {
					Toast.makeText(PostNewMsg.this,
							"Can not Locating, please check your GPS first",
							Toast.LENGTH_LONG).show();
				} else {
					if (msgcontent.getText().toString().equals("")) {
						Toast.makeText(
								PostNewMsg.this,
								"Message can not be empty, please add something",
								Toast.LENGTH_LONG).show();
					} else {
						PostStatusMsg postnewmsg = new PostStatusMsg(0,
								msgcontent.getText().toString(), ts, range,
								msglocation);
						result = postnewmsg.postMsg();
						if (result.isFlag() == true) {
							Toast.makeText(PostNewMsg.this,
									"Message has been successfully posted",
									Toast.LENGTH_LONG).show();
							//set all elements empty
							msgcontent.setText("");
							msglocatext.setText("");
							s_rg.setSelection(1);
							longitude.setText("0");
							latitude.setText("0");

						} else {
							 Toast.makeText(PostNewMsg.this,
							 result.getMessage(),
							 Toast.LENGTH_LONG).show();
						}
					}
				}
				//System.out.println(currentLongitude);
				//System.out.println(range);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_new_msg, menu);
		return true;
	}

}
