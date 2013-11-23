package com.androidbegin.gcmtutorial;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ToggleButton;

public class LightActivity extends Activity {
	final Context context = this;
	private static final String[][] data = { { "LIVINGROOM", "KITCHEN" } };
	private ExpandableListView expandableListView;

	// Dialog
	private static ToggleButton switchButton;
	private Dialog switchDialog;
	private static String signal = "";
	static String TAG = "GCM Tutorial::Activity";

	// handler to access the UI Thread

	private int lastOpenedItem = -1;
	private boolean lastState = false;
	private static Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light);

		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		expandableListView.setAdapter(new SampleExpandableListAdapter(context,
				this, data));
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				switchDialog = new Dialog(LightActivity.this,
						R.style.PauseDialog);
				switchDialog.setContentView(R.layout.dialog);

				if (childPosition == 0) {
					lastOpenedItem = 0; // //to remeber which item has been
										// selected last time
					switchDialog.setTitle("LIVINGROOM");
					signal = "L";
					// switchDialog
				} else if (childPosition == 1) {
					lastOpenedItem = 1; // to remeber which item has been
										// selected last time
					switchDialog.setTitle("KITCHEN");
					signal = "K";
				}

				switchDialog.show();

				switchButton = (ToggleButton) switchDialog
						.findViewById(R.id.switch_button);
				switchButton
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							// Send message to Server(Switch On)
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (signal.charAt(0) == 'L') {
									if (isChecked) {
										signal = "LN";

									} else
										signal = "LF";
								} else if (signal.charAt(0) == 'K') {
									if (isChecked)
										signal = "KN";
									else
										signal = "KF";
								}

								new MyAsyncTask().execute(signal);
							}
						});

				return false;
			}
		});
	}

	static void updateTheView(String message,final String light, final String state) {
		Log.i(TAG, "the GCM has triggered the button");
		new Thread() {
			@Override
			public void run() {

				handler.post(new Runnable() {
					@Override
					public void run() {

						Log.i(TAG,
								"background method has started to toggle the button");
						if (signal.charAt(0) == 'L' && light.equals("1")) {
							if (switchButton.isChecked() && state.equals("off")) {
								signal = "LN";
								switchButton.toggle();
							} else {
								if (!(switchButton.isChecked())
										&& state.equals("on")) {
									signal = "LF";
									switchButton.toggle();
								}
							}
						} else if (signal.charAt(0) == 'K') {
							if (switchButton.isChecked() && state.equals("off")) {
								signal = "KN";
								switchButton.toggle();
							} else {
								if (!(switchButton.isChecked())
										&& state.equals("on")) {
									signal = "KF";
									switchButton.toggle();
								}
							}
						}
					}
				});
			};
		}.start();
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

		String response = "";

		@Override
		protected Double doInBackground(String... params) {

			postData(params[0]);
			return null;
		}

		public void postData(String toPost) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			
			
			String deviceId="APA91bGTZAtSLOhNTO0yS7qACd_BO4oMo8nStuhqE7FG72QVrI-tLC6Is-zi7qCoSgcC1JpGi_dHzEIk8ptXr38tFUV2GkR2J9AOUb2LZkCbtjdqDGccEn8MKqUmrv2wf5_11c6BfsStkrS8TfVgbuBm8NgcKj6wudcEZOwcMBpFPRiayG95sWU";
			String port="3000"; //port
			String ip="http://10.120.84.4"; //ip adsress
			String lightId="1"; 
			Log.i("sending request to",ip+":"+port+"/"+deviceId+"/"+lightId+"/toggle");
			String requestAddress=ip+":"+port+"/"+deviceId+"/"+lightId+"/toggle";
			
			HttpPost httppost = new HttpPost(requestAddress);

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs.add(new BasicNameValuePair("action", toPost));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = httpclient.execute(httppost, responseHandler);
				Log.i("RESPONSE", "RESPONSE from SERVER : " + response);
				// Toast.makeText(MainActivity.this, "response" + reverseString,
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}