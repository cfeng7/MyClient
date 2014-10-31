package com.example.myclient;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

/***********************************************************************
 * @author: Cong Feng Purpose: This class is mainly for construct the tabhost
 *          frame, new tab could be added in this class
 ***********************************************************************/
@SuppressWarnings("deprecation")
public class MainTab extends TabActivity {
	private TabSpec notifSpec;
	TabHost client_tab;
	 private Intent intent;
	 
	 
	 
    private BroadcastReceiver receiver =new BroadcastReceiver() {
        
       
		@Override
        public void onReceive(Context context, Intent intent) {
            int num = intent.getIntExtra("name", 0);//获得后台传过来的，键值为name对应的值
           // tv.setText(num+"");//更新主界面UI
            if (num==5){
           	System.out.println("print_color");
//        		notifSpec = client_tab.newTabSpec("notif");
//    
//        		notifSpec.setIndicator("",
//        				getResources().getDrawable(R.drawable.ic_action_notif));
//        		// notifSpec.setIndicator("notif");
        		ImageView ic = (ImageView) client_tab.getTabWidget().getChildTabViewAt(3).findViewById(android.R.id.icon);
        		//ic.setBackgroundResource(R.drawable.ic_action_notif_background);
        		  ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_notf));
        		//Color color =new Color();
        		//ic.setBackgroundColor(color.GRAY);
     
        	
            }
        }
    };
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);
		client_tab = getTabHost();
		final TabWidget tabWidget = client_tab.getTabWidget();
		registerReceiver(receiver, new IntentFilter("action111"));
        intent = new Intent(MainTab.this, NotificationList.class);
		
        //add tabhost change listener
 
        client_tab.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String id) {
				// TODO Auto-generated method stub
			    if("notif".equals(id)) {
	        		ImageView ic = (ImageView) client_tab.getTabWidget().getChildTabViewAt(3).findViewById(android.R.id.icon);
	        		//ic.setBackgroundResource(R.drawable.ic_action_notif_background);
	        		  ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_unnotf));
			        //destroy earth
			    }
			}
        	
        });
        
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).getLayoutParams().height = 60;
			tabWidget.getChildAt(i).getLayoutParams().width = 65;
		}

		// client_tab.getTabWidget().getChildAt(0).getLayoutParams().width = 50;
		TabSpec myProSpec = client_tab.newTabSpec("mypro");
		myProSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_prof));
		// myProSpec.setIndicator("Profile");
		Intent myProIntent = new Intent(this, MyProfile.class);
		myProSpec.setContent(myProIntent);

		notifSpec = client_tab.newTabSpec("notif");
		notifSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_unnotf));
		// notifSpec.setIndicator("notif");
		Intent notifIntent = new Intent(this, NotificationList.class);
		notifSpec.setContent(notifIntent);

		TabSpec MyMsgSpec = client_tab.newTabSpec("mymsg");
		MyMsgSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_mymsg));
		// postMeSpec.setIndicator("post");
		Intent postMeIntent = new Intent(this, MyMsgList.class);
		MyMsgSpec.setContent(postMeIntent);

		TabSpec refMeSpec = client_tab.newTabSpec("refreshme");
		refMeSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_allms));
		// refMeSpec.setIndicator("refresh");
		Intent refMeIntent = new Intent(this, MsgListView.class);
		refMeSpec.setContent(refMeIntent);

		TabSpec PostSpec = client_tab.newTabSpec("postmsg");
		PostSpec.setIndicator("",
				getResources().getDrawable(R.drawable.ic_action_post));
		// refMeSpec.setIndicator("refresh");
		Intent postIntent = new Intent(this, PostNewMsg.class);
		PostSpec.setContent(postIntent);

		TabSpec MapSpec = client_tab.newTabSpec("googlemap");
		MapSpec.setIndicator("Map",
				getResources().getDrawable(R.drawable.ic_action_post));
		// refMeSpec.setIndicator("refresh");
		Intent mapIntent = new Intent(this, MyGoogleMap.class);
		MapSpec.setContent(mapIntent);

		client_tab.addTab(PostSpec);
		client_tab.addTab(MyMsgSpec);
		client_tab.addTab(refMeSpec);
		client_tab.addTab(notifSpec);
		client_tab.addTab(myProSpec);
		client_tab.addTab(MapSpec);
		client_tab.setCurrentTab(1);
	}

	class broadcastreceiver extends BroadcastReceiver {
		public broadcastreceiver() {
			notifSpec.setIndicator("",
					getResources().getDrawable(R.drawable.ic_action_post));
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}

	}
}
