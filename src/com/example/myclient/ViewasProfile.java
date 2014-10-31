package com.example.myclient;

import com.example.myclientmodel.Profile;

import clientactive.ViewProfile;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ViewasProfile extends Activity {
    private String nickername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewas_profile);
		TextView name=(TextView) findViewById(R.id.textView_profileview_name);
		TextView gender=(TextView) findViewById(R.id.textView_profileview_gender);
		TextView age=(TextView) findViewById(R.id.textView_profileview_age);
		TextView status=(TextView) findViewById(R.id.textView_profile_view_status);
		Intent nextpage = getIntent();
		if (nextpage!=null){
			
			try {
				nickername=(String) nextpage.getExtras().get("msgname");
				System.out.println(nickername);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ViewProfile viewprofile=new ViewProfile(this.nickername);
			viewprofile.severtoprofile();
			Profile profile=viewprofile.getprofile();
			name.setText("NICKNAME: "+profile.getNickName());
			String sex;
			if (profile.getGender().equals("f")){
				sex="Female";
			}
			else{
				sex="Male";
			}
			gender.setText("GENDER: "+sex);
			//System.out.println(profile.getAge());
			age.setText("AGE: "+Integer.toString(profile.getAge()));
			status.setText("SELFDESCRIPTION: "+profile.getSelfDiscription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewas_profile, menu);
		return true;
	}

}
