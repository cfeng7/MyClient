package com.example.myclient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clientactive.GetNotify;
import clientactive.RequireNotif;



import com.example.myclientmodel.Location;
import com.example.myclientmodel.StatusMessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Purpose: This class is mainly showing the new message that connected with the user
 ***********************************************************************/
public class NotificationList extends Activity {
    static List<StatusMessage> notifsmsglist=new ArrayList<StatusMessage>();
    static List<String> notifnicknamelist=new ArrayList<String>();
    private TextView num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_list);
		num=(TextView) findViewById(R.id.textView_num);
		populatesmsglist();
		populateListView();
		ClickOneItem();
		ClicklongoneItem();
		new Thread(new MySendThread1()).start();//new thread
		
		
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
		notifsmsglist.add(new StatusMessage(15,"this is mytext1",ts,1500,loc1));
		notifsmsglist.add(new StatusMessage(15,"this is mytext2",ts,1500,loc2));
		notifsmsglist.add(new StatusMessage(15,"this is mytext3",ts,1500,loc3));
		notifsmsglist.add(new StatusMessage(15,"this is mytext4",ts,1500,loc3));
		notifnicknamelist.add("PETER");
		notifnicknamelist.add("MIKE");
		notifnicknamelist.add("JACK");
		notifnicknamelist.add("LUCY");
		
		
	}
	/**
	 * add the arrayadpater on the list view, then the message on list view will
	 * be visible
	 */
	public void populateListView(){
		ArrayAdapter<StatusMessage> smsgadapter=new MyListAdapter();
		ListView list= (ListView) findViewById(R.id.listViewnotification);
		list.setAdapter(smsgadapter);//set adapter to listview for viewing
	}
	/**
	 * a listener just for test
	 */
	private void ClickOneItem(){//add itemlistener to each item
		ListView list=(ListView) findViewById(R.id.listViewnotification);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {			
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= notifsmsglist.get(position);
				//smsglist.remove(position);
				String name= notifnicknamelist.get(position);
				long msgid= currentsmsg.getId();
				nextpage.setClass(NotificationList.this, Comment_list.class);
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
		ListView list=(ListView) findViewById(R.id.listViewnotification);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View viewclicked,
					int position, long id) {
				Intent nextpage=new Intent();
				StatusMessage currentsmsg= notifsmsglist.get(position);
				String name= notifnicknamelist.get(position);
				nextpage.setClass(NotificationList.this, ViewasProfile.class);
				nextpage.putExtra("msgname", name);
				startActivity(nextpage);	
				notifsmsglist.remove(position);
				populateListView();
				return true;
			}
		});
	}
	/**
	 * arrayadapter which is used to assign all the essential elements on the item_view
	 */
	private class MyListAdapter extends ArrayAdapter<StatusMessage>{

		public MyListAdapter() {
			super(NotificationList.this, R.layout.item_view,notifsmsglist);//composed by item_view, showing smsglist
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView= convertView;
			if  (itemView==null){
				itemView=getLayoutInflater().inflate(R.layout.item_view, parent, false);
			}
			//return super.getView(position, convertView, parent);
			StatusMessage currentsmsg= notifsmsglist.get(position);
			TextView contentview= (TextView) itemView.findViewById(R.id.gender);
			TextView nicknameview=(TextView) itemView.findViewById(R.id.textView_nickname);
			TextView timeview=(TextView) itemView.findViewById(R.id.textView_time);
			TextView placeview= (TextView) itemView.findViewById(R.id.place);
			TextView latitudeview= (TextView) itemView.findViewById(R.id.latitude);
			TextView longitudeview= (TextView) itemView.findViewById(R.id.longitude);
			
			contentview.setText(currentsmsg.getContent());
			nicknameview.setText(notifnicknamelist.get(position));
			timeview.setText(currentsmsg.getDate().toString());
			placeview.setText(currentsmsg.location.getTextDiscription());
			latitudeview.setText(Double.toString(currentsmsg.location.getLatitude()));
			longitudeview.setText(Double.toString(currentsmsg.location.getLongitude()));
			
			
			return itemView;
			
		}
		

		
	}
	
	
	
	
	
	
	
	
	public class MySendThread1 implements Runnable {
		//public static List<StatusMessage> newsmsg;
		public int p=1;
		private Handler handler;
		public MySendThread1(){
			this.handler = new Handler() {
			    public void handleMessage(Message msg) {
					  super.handleMessage(msg);
					  Bundle b=msg.getData();
					  NotificationList.this.num.setText(""+b.getInt("pnum"));
					  //MainActivity.this.testbox.setText(""+b.getInt("pnum"));
			    }
			};
		}
	    @Override
	    public void run() {
	        // TODO Auto-generated method stub
	        while (true) {
	            try {
	                Thread.sleep(10000);// 10s
	               // System.out.println("work");
	               if(p==1){ 
	            	  int index=5;
	                  Intent i1 = new Intent();// 不能进行页面的跳转，只能实例化成这样
	                  i1.setAction("action111");// 设置Intent对象的action属性，以便于在主界面做匹配
	                  i1.putExtra("name", index);// 携带数据
	                  sendBroadcast(i1);// 发送广播
	               }
	                  
	                GetNotify require=new GetNotify();
	                require.severtosmsg();            
	                if (require.jsontonickname().size()!=0){
	              try {
	            	  NotificationList.notifsmsglist=require.jsontosmsglist();
	            	  NotificationList.notifnicknamelist=require.jsontonickname();

	                  
					populateListView();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}         
	                }
	                else{
	                	this.p++;
	                	Message msg = new Message();
	                	Bundle b=new Bundle();
	                	b.putInt("pnum", p);
	                	msg.setData(b);
	                	this.handler.sendMessage(msg);
	                    //handler.sendMessage(msg);
	                	//System.out.println("emptylist");
	                }
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.msg_list_view, menu);
		return true;
	}

}
