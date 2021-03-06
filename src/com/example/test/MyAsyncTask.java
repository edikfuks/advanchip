package com.example.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<String, Integer, Double> {
		
	String response = "";
		
	@Override
	protected Double doInBackground(String... params) {
		// TODO Auto-generated method stub
		postData(params[0]);
		return null;
	}

	public void postData(String toPost) {
		
		String deviceId=Constants.deviceID;
		String port="3000"; //port
		String ip=Constants.ip; //ip adsress
		String lightId="1"; 
		String output;
		
		Log.i("sending request to",ip+":"+port+"/"+deviceId+"/"+lightId+"/toggle");
		String requestAddress=ip+":"+port+"/"+deviceId+"/"+lightId+"/toggle";
		
		StringBuilder url = new StringBuilder();
		url.append(requestAddress);
		HttpGet get = new HttpGet(url.toString());
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest req = get;
		HttpResponse resp = null;
		try {
			resp = client.execute(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("TEST", "resp:" + resp.toString());
		HttpEntity httpEntity = resp.getEntity();
	    try {
			output = EntityUtils.toString(httpEntity);
			Log.i("response",output);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LightActivity.toggleButton();
	}
}