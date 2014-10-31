package clientactive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

/**
 * This class used asynchronous thread to get address name from google map server
 * @author Lezhong Huang
 *
 */
public class JSONParser extends AsyncTask<String, Void, JSONObject> {
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	private static String Address1 = "", Address2 = "", City = "", State = "",
			Country = "", County = "", PIN = "", WrongMsg = "init";
	private FragmentActivity fatherActivity = null;
	private ProgressDialog dialog = null;
	private GoogleMap mMap = null;

	/**
	 * Set parent activity and google map instance
	 * @param fatherAct parent activity
	 * @param mMap google map instance
	 */
	public JSONParser(FragmentActivity fatherAct, GoogleMap mMap) {
		fatherActivity = fatherAct;
		this.mMap = mMap;
	}

	/**
	 * called this thread doing real things, and it shows a waiting dialog to tell users 
	 * they need to wait until address name is gotten from internet.
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(fatherActivity);
		dialog.show();
	}

	/**
	 * get address name from given url and encloses the address name as JSONObject
	 */
	@Override
	protected JSONObject doInBackground(String... params) {
		String[] url = params;
		// TODO Auto-generated method stub
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url[0]);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			//Dump.Str(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			JSONParser.jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String

		return jObj;
	}

	/**
	 * called after doInBackground. It parse gotten JSONObject to string and add a marker
	 * to map with address name as title.
	 */
	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// TODO Auto-generated method stub
		JSONObject jsonObj = result;
		if (jsonObj != null) {
			try {
				String Status = jsonObj.getString("status");
				if (Status.equalsIgnoreCase("OK")) {
					WrongMsg = "succeed";
					JSONArray Results = jsonObj.getJSONArray("results");
					JSONObject zero = Results.getJSONObject(0);
					JSONArray address_components = zero
							.getJSONArray("address_components");
					JSONObject geometry = zero.getJSONObject("geometry");
					JSONObject location = geometry.getJSONObject("location");
					String longitude = location.getString("lng");
					String latitude = location.getString("lat");
					Double longitudeValue = Double.parseDouble(longitude);
					Double latitudeValue = Double.parseDouble(latitude);

					for (int i = 0; i < address_components.length(); i++) {
						JSONObject zero2 = address_components.getJSONObject(i);
						String long_name = zero2.getString("long_name");
						JSONArray mtypes = zero2.getJSONArray("types");
						String Type = mtypes.getString(0);

						if (TextUtils.isEmpty(long_name) == false
								|| !long_name.equals(null)
								|| long_name.length() > 0 || long_name != "") {
							if (Type.equalsIgnoreCase("street_number")) {
								Address1 = long_name + " ";
							} else if (Type.equalsIgnoreCase("route")) {
								Address1 = Address1 + long_name;
							} else if (Type.equalsIgnoreCase("sublocality")) {
								Address2 = long_name;
							} else if (Type.equalsIgnoreCase("locality")) {
								// Address2 = Address2 + long_name + ", ";
								City = long_name;
							} else if (Type
									.equalsIgnoreCase("administrative_area_level_2")) {
								County = long_name;
							} else if (Type
									.equalsIgnoreCase("administrative_area_level_1")) {
								State = long_name;
							} else if (Type.equalsIgnoreCase("country")) {
								Country = long_name;
							} else if (Type.equalsIgnoreCase("postal_code")) {
								PIN = long_name;
							}
						}
						String strReturnedAddress = JSONParser.getAddress1()
								+ ", " + JSONParser.getAddress2();
						mMap.addMarker(new MarkerOptions()
								.position(
										new LatLng(latitudeValue,
												longitudeValue))
								.title(strReturnedAddress).snippet("bill")
								.draggable(true));
						dialog.dismiss();
					}
				} else {
					WrongMsg = "sble";
				}
			} catch (Exception ex) {

			}
		}
		try {
			Log.i("hello", result.toString());
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// dialog.dismiss();
	}

	public static String getAddress1() {
		return Address1;

	}

	public static String getAddress2() {
		return Address2;

	}

	public static String getCity() {
		return City;

	}

	public static String getState() {
		return State;

	}

	public static String getCountry() {
		return Country;

	}

	public static String getCounty() {
		return County;

	}

	public static String getPIN() {
		return PIN;

	}

	public static String getWrongMsg() {
		return WrongMsg;
	}
}
