package com.example.myclient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clientactive.GetAllStatusMessage;

import com.example.myclientmodel.Location;
import com.example.myclientmodel.StatusMessage;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is mainly for showing all the status messages list in the listview,
 * a refresh button is added to refresh the message list.
 ***********************************************************************/
public class MsgListView extends Activity {
    public static List<StatusMessage> smsglist=new ArrayList<StatusMessage>();
    public static List<String> nicknamelist=new ArrayList<String>();
    private Button refreshbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_list_view);
		refreshbutton=(Button) findViewById(R.id.button_refreshmymsg_msglist);
		populatesmsglist();
		populateListView();
		System.out.println("button1");
		refreshbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("button");
				// TODO Auto-generated method stub
				if(MyGoogleMap.getCurrentLatitude()==0&&MyGoogleMap.getCurrentLongitude()==0){
					Toast.makeText(MsgListView.this, "Please open GPS first ", Toast.LENGTH_LONG).show();	
				}else{
				GetAllStatusMessage getallmsg=new GetAllStatusMessage();
				getallmsg.severtosmsg();
				try {
					smsglist=getallmsg.jsontosmsglist();
					nicknamelist=getallmsg.jsontonickname();
					System.out.println("yes");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				populateListView();
				
				for(int i=0;i<smsglist.size();i++){
					StatusMessage ms=smsglist.get(i);
					System.out.println(ms.getContent());
				}
				
				
				
				
				
				}
			}
		});
		
		
	
		ClickOneItem();
		ClicklongoneItem();
	}
	/**
	 * put some message to message list for initialing.
	 */
	public void populatesmsglist(){
		Location loc1=new Location(-76.61,39.31,"ALC");
		Location loc2=new Location(-76.62,39.31,"BLC");
		Location loc3=new Location(-76.63,39.31,"CLC");
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
		ListView list= (ListView) findViewById(R.id.smsglistView1);
		list.setAdapter(smsgadapter);//set adapter to listview for viewing
	}
	/**
	 * a listener just for test
	 */
	private void ClickOneItem(){//add itemlistener to each item
		ListView list=(ListView) findViewById(R.id.smsglistView1);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {			
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= smsglist.get(position);
				String name= nicknamelist.get(position);
				long msgid= currentsmsg.getId();
				nextpage.setClass(MsgListView.this, Comment_list.class);
				nextpage.putExtra("msgname", name);
				nextpage.putExtra("msgcontent", currentsmsg.getContent());
				nextpage.putExtra("msgid", currentsmsg.getId());
				startActivity(nextpage);
			}
		});
		
	}
	/**
	 * a listener just for test
	 */
	private void ClicklongoneItem() {
		ListView list=(ListView) findViewById(R.id.smsglistView1);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= smsglist.get(position);
				String name= nicknamelist.get(position);
				nextpage.setClass(MsgListView.this, ViewasProfile.class);
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
			super(MsgListView.this, R.layout.item_view,smsglist);//composed by item_view, showing smsglist
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
