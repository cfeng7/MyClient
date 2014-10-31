package com.example.myclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/***********************************************************************
 * @author:  Cong Feng
 * Purpose: This class is a interface for welcoming the users, which currently empty and will be 
 * refined later
 ***********************************************************************/
public class Welcomepage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcomepage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcomepage, menu);
		return true;
	}


}
