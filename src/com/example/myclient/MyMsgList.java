package com.example.myclient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clientactive.GetMyStatusMessage;

import com.example.myclientmodel.Location;
import com.example.myclientmodel.StatusMessage;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is similar with the MsgListView, which is used to show all the message
 * posted by the users
 ***********************************************************************/
public class MyMsgList extends Activity {
    private List<StatusMessage> smsglist=new ArrayList<StatusMessage>();
    private List<String> nicknamelist=new ArrayList<String>();
    private Button refreshbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_msg_list);
		refreshbutton=(Button) findViewById(R.id.button_refresh_mymsg);
		populatesmsglist();
		populateListView();
		ClickOneItem();
		ClicklongoneItem();
		
		refreshbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ask for mymsg request and get the msg,nickname list
				GetMyStatusMessage getmymsg=new GetMyStatusMessage();
				getmymsg.severtosmsg();
				try {
					smsglist=getmymsg.jsontosmsglist();
					nicknamelist=getmymsg.jsontonickname();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    populateListView();
			}
		});
	}
	/**
	 * put some message to message list for initialing.
	 */
	public void populatesmsglist(){
		Location loc1=new Location(10,10,"ALC");
		Location loc2=new Location(10,10,"BLC");
		Location loc3=new Location(10,10,"CLC");
		//String timeStr = "2010-06-23 13:18:33.112233";  
		//Timestamp ts = Timestamp.valueOf(timeStr);
		Timestamp ts= new Timestamp(new Date().getTime());
		smsglist.add(new StatusMessage(15,"this is mytext1",ts,1500,loc1));
		smsglist.add(new StatusMessage(15,"this is mytext2",ts,1500,loc2));
		smsglist.add(new StatusMessage(15,"this is mytext3",ts,1500,loc3));
		smsglist.add(new StatusMessage(15,"this is mytext4",ts,1500,loc3));
		nicknamelist.add("PETER");
		nicknamelist.add("MIKE");
		nicknamelist.add("JACK");
		nicknamelist.add("LUCY");
		
	}
	/**
	 * add the arrayadpater on the list view, then the message on list view will
	 * be visible
	 */
	public void populateListView(){
		ArrayAdapter<StatusMessage> smsgadapter=new MyListAdapter();
		ListView list= (ListView) findViewById(R.id.listview_mymsg);
		list.setAdapter(smsgadapter);//set adapter to listview for viewing
	}
	
	
	/**
	 * a listener just for test
	 */
	private void ClickOneItem(){//add itemlistener to each item
		ListView list=(ListView) findViewById(R.id.listview_mymsg);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {	
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= smsglist.get(position);
				String name= nicknamelist.get(position);
				long msgid= currentsmsg.getId();
				nextpage.setClass(MyMsgList.this, Comment_list.class);
				nextpage.putExtra("msgname", name);
				nextpage.putExtra("msgcontent", currentsmsg.getContent());
				nextpage.putExtra("msgid", currentsmsg.getId());
				startActivity(nextpage);
		//		StatusMessage currentsmsg= smsglist.get(position);
	      //   new AlertDialog.Builder(MyMsgList.this).setMessage("This is "+currentsmsg.getContent()+"\n").setPositiveButton("Close", null).show(); 
		//	Toast.makeText(MsgListView.this, "wangwangwang", Toast.LENGTH_LONG).show();	
			}
		});
		
	}
	/**
	 * a listener just for test
	 */
	private void ClicklongoneItem() {
		ListView list=(ListView) findViewById(R.id.listview_mymsg);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= smsglist.get(position);
				String name= nicknamelist.get(position);
				nextpage.setClass(MyMsgList.this, ViewasProfile.class);
				nextpage.putExtra("msgname", name);
				startActivity(nextpage);		
				return true;
			}
		});
	}
	/**
	 * arrayadapter which is used to assign all the essential elements on the item_view
	 */
	private class MyListAdapter extends ArrayAdapter<StatusMessage>{

		public MyListAdapter() {
			super(MyMsgList.this, R.layout.item_view,smsglist);//composed by item_view, showing smsglist
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView= convertView;
			if  (itemView==null){
				itemView=getLayoutInflater().inflate(R.layout.item_view, parent, false);
			}
			//return super.getView(position, convertView, parent);
			StatusMessage currentsmsg= smsglist.get(position);
			TextView contentview= (TextView) itemView.findViewById(R.id.gender);
			TextView nicknameview=(TextView) itemView.findViewById(R.id.textView_nickname);
			TextView timeview=(TextView) itemView.findViewById(R.id.textView_time);
			TextView placeview= (TextView) itemView.findViewById(R.id.place);
			TextView latitudeview= (TextView) itemView.findViewById(R.id.latitude);
			TextView longitudeview= (TextView) itemView.findViewById(R.id.longitude);
			
			contentview.setText(currentsmsg.getContent());
			nicknameview.setText(nicknamelist.get(position));
			timeview.setText(currentsmsg.getDate().toString());
			placeview.setText(currentsmsg.location.getTextDiscription());
			latitudeview.setText(Double.toString(currentsmsg.location.getLatitude()));
			longitudeview.setText(Double.toString(currentsmsg.location.getLongitude()));
			
			
			return itemView;
			
		}
		

		
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.msg_list_view, menu);
		return true;
	}

}
