package com.example.myclientmodel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * a class restore the result of some operation
 * @author daiyang
 *  Edited:  CongFeng
 */
public class ResultInfo {
	private String message;
	private boolean flag;
	/**
	 * constructor
	 * @param message contains feedback details
	 * @param flag a boolean indicates whether the connection succeed.
	 */
	public ResultInfo(String message, boolean flag) {
		super();
		this.message = message;
		this.flag = flag;
	}
	
	public ResultInfo(JSONObject rinfojson) {
		super();
        try {
			message=rinfojson.getString("message");
			flag=rinfojson.getBoolean("flag");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * This class mainly for transform a ResultInfo to JSONObject
	 * @return ResultInfojson as the  JSONObject 
	 */ 
	public JSONObject toJson(){
		//wait to be finished
		JSONObject resultInfojson=new JSONObject();
		try {
			resultInfojson.put("message", this.message);
			resultInfojson.put("flag", this.flag);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultInfojson;
	}
	
	
}
