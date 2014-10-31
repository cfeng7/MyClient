package com.example.myclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.widget.Toast;
import clientactive.JSONParser;

import com.example.myclientmodel.StatusMessage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
//import com.example.googlemap8.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.google.android.support.v4.app.FragmentManager
/**
 * This class is used to localize user, and show the location on google map.
 * Additionally, it can also add mark to map with address name as title.
 * @author Lehzong Huang
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyGoogleMap extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener,
		OnMyLocationButtonClickListener,
		com.google.android.gms.location.LocationListener {
	private String provider;
	private SupportMapFragment mg;
	private LocationManager locationManager;
	//public static List<StatusMessage> smsglist;
	// private LocationListener locationListener;
	GoogleMap mMap;
	private CameraPosition cameraPosition;
	private MarkerOptions markerOpt;
	private LocationClient mLocationClient;
	// private ;
	private Object fg;
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	private static double currentLongitude = 0;
	private static double currentLatitude = 0;
	//private EditText longitude = null;
	//private EditText latitude = null;
	/**
	 * Open GPS function, and show google map, and set property of the map.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_google_map);
		openGPSSettings();
		fg = getSupportFragmentManager().findFragmentById(R.id.mapView);
		if (fg != null) {
			// longitude.setText("niubi");
			mMap = ((SupportMapFragment) fg).getMap();
			mMap.clear();
			mMap.setMyLocationEnabled(true);
			mMap.setOnMyLocationButtonClickListener(this);
		}
		
		
//		markerOpt = new MarkerOptions();
//		markerOpt.position(new LatLng(currentLatitude, currentLongitude));
//		markerOpt.draggable(true);
//		markerOpt.visible(true);
//		markerOpt.anchor(0.5f, 0.5f);// set picture center
//		mMap.addMarker(markerOpt);
//		cameraPosition = new CameraPosition.Builder()
//				.target(new LatLng(currentLatitude, currentLongitude)).zoom(15) // ratio
//				.bearing(0) // Sets the orientation of the camera to
//							// east
//				.tilt(30) // Sets the tilt of the camera to 30 degrees
//				.build(); // Creates a CameraPosition from the builder
//		mMap.animateCamera(CameraUpdateFactory
//				.newCameraPosition(cameraPosition));
		
		
//		final Button button = (Button) findViewById(R.id.getlocation);
//		longitude = (EditText) findViewById(R.id.longitude);
//		latitude = (EditText) findViewById(R.id.latitude);
//		longitude.setText("" + currentLongitude);
//		latitude.setText(""+currentLatitude);
//		 final Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
//		button.setOnClickListener(new ButtonClick(this, longitude, latitude));
	}
	
//	class ButtonClick implements View.OnClickListener {
//		private FragmentActivity father;
//		private EditText longitude;
//		private EditText latitude;
//
//		public ButtonClick(FragmentActivity father, EditText longitude,
//				EditText latitude) {
//			// TODO Auto-generated constructor stub
//			this.father = father;
//			this.latitude = latitude;
//			this.longitude = longitude;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			openGPSSettings();
//			double longitudeValue = Double.parseDouble(this.longitude.getText()
//					.toString());
//			double latitudeValue = Double.parseDouble(this.latitude.getText()
//					.toString());
//			// json get address name
//
//			try {
//				new JSONParser(father, mMap).execute(
//						"http://maps.googleapis.com/maps/api/geocode/json?latlng="
//								+ latitudeValue + "," + longitudeValue
//								+ "&sensor=true");
//				//JSONParser.doIt(res);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			markerOpt = new MarkerOptions();
//			markerOpt.position(new LatLng(latitudeValue, longitudeValue));
//			markerOpt.draggable(true);
//			markerOpt.visible(true);
//			markerOpt.anchor(0.5f, 0.5f);// set picture center
//			cameraPosition = new CameraPosition.Builder()
//					.target(new LatLng(latitudeValue, longitudeValue)).zoom(17) // ratio
//					.bearing(0) // Sets the orientation of the camera to
//								// east
//					.tilt(30) // Sets the tilt of the camera to 30 degrees
//					.build(); // Creates a CameraPosition from the builder
//			mMap.animateCamera(CameraUpdateFactory
//					.newCameraPosition(cameraPosition));
//			// mMap.addMarker(markerOpt);
//			mMap.addMarker(new MarkerOptions()
//					.position(
//							new LatLng(latitudeValue - 0.00001,
//									longitudeValue + 0.0001))
//					.title("center")
//					.icon(BitmapDescriptorFactory
//							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//		}
//	}
	/**
	 * Open GPS function of the Android device.
	 */
	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS works", Toast.LENGTH_SHORT).show();
			return;
		} else {
			Toast.makeText(this, "please open GPS", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivityForResult(intent, 0);
		}
	}
	public void markmap(){
		List<StatusMessage>smsglist1=MsgListView.smsglist;
		List<String>nicklist1=MsgListView.nicknamelist;
//		 for(StatusMessage smsg:smsglist1){  
//			double lo= smsg.getLocation().getLongitude();  
//			double la=smsg.getLocation().getLatitude();
//			String text=smsg.getContent();
//			markerOpt = new MarkerOptions();
//			markerOpt.position(new LatLng(la, lo));
//			markerOpt.title(text);
//			markerOpt.draggable(true);
//			markerOpt.visible(true);
//			markerOpt.anchor(0.5f, 0.5f);// set picture center
//			mMap.addMarker(markerOpt);
//			System.out.println(lo);
//			System.out.println(la);
//			    } 
		 for(int i=0;i<smsglist1.size();i++){
			 StatusMessage smsg=smsglist1.get(i);
			 String nickname=nicklist1.get(i);
			double lo= smsg.getLocation().getLongitude();  
			double la=smsg.getLocation().getLatitude();
			String text=smsg.getContent();
			markerOpt = new MarkerOptions();
			markerOpt.position(new LatLng(la, lo));
			markerOpt.title(nickname+":'"+text+"'");
			markerOpt.draggable(true);
			markerOpt.visible(true);
			markerOpt.anchor(0.5f, 0.5f);// set picture center
			mMap.addMarker(markerOpt);
			//System.out.println(lo);
			//System.out.println(la);
			    } 
	}
	/**
	 * Get last location of user.
	 */
	public void showMyLocation() {
		if (mLocationClient != null && mLocationClient.isConnected()) {
			while(mLocationClient.getLastLocation() == null){}
			Location currentLocation = mLocationClient.getLastLocation();
			currentLongitude = currentLocation.getLongitude();
			currentLatitude = currentLocation.getLatitude();
			String msg = "Location = " + mLocationClient.getLastLocation();
		}
		
	}
	/**
	 * Create a location client instance
	 */
	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	/**
	 * called when the activity is recalled, and in this function, it set connection of 
	 * location client.
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//if (mLocationClient != null) {
		//	mLocationClient.disconnect();
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * called according to the setting of UpdatesRequest, in this situation, it will be called each 5 seconds.
	 * In this function, it move the map to user's current location.
	 */
	@Override
	public void onLocationChanged(Location arg0) {
		showMyLocation();
		//longitude.setText("" + currentLongitude);
		//latitude.setText(""+currentLatitude);
		cameraPosition = new CameraPosition.Builder()
		.target(new LatLng(currentLatitude, currentLongitude)).zoom(15) // ratio
		.bearing(0) // Sets the orientation of the camera to
					// east
		.tilt(30) // Sets the tilt of the camera to 30 degrees
		.build(); // Creates a CameraPosition from the builder
mMap.animateCamera(CameraUpdateFactory
		.newCameraPosition(cameraPosition));
         this.markmap();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	/**
	 * called when location client is connected, it sets the parameters of requestLocationUpdates, and
	 * move the map to current last location.
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener
		showMyLocation();
		//longitude.setText(""+currentLongitude);
		//latitude.setText(""+currentLatitude);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	public static double getCurrentLongitude() {
		return currentLongitude;
	}


	public static double getCurrentLatitude() {
		return currentLatitude;
	}



}
