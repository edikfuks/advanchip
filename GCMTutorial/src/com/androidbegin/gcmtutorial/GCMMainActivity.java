package com.androidbegin.gcmtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gcm.GCMRegistrar;

/**
 * 
 * @author Edward Fuks
 * 
 */
public class GCMMainActivity extends Activity {

	String TAG = "GCM Tutorial::Activity";
	static GCMMainActivity mainActivity;

	private Handler handler = new Handler();
	private TextView textView;
	private ToggleButton toggleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainActivity = this;
		setContentView(R.layout.activity_gcmmain);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		GCMIntentService.getContext(this);

		// Register Device Button
		Button regbtn = (Button) findViewById(R.id.register);

		// text label which is under the toggle button
		// it shows the GCM message text when it arrives from Google
		textView = (TextView) findViewById(R.id.textView2);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

		// this is a toggle button to turn on or off the light.
		// every time you click it, you should send the http request to the
		// server
		toggleButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// http code goes here
				Log.i(TAG, "toggleButton was clicked");
				sendRequestToServer();

			}

		});

		// registering the device with GCM server
		regbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Registering device");
				// Retrive the sender ID from GCMIntentService.java
				// Sender ID will be registered into GCMRegistrar
				GCMRegistrar.register(GCMMainActivity.this,
						GCMIntentService.SENDER_ID);

			}
		});

	}

	/**
	 * Trying to send HTTP
	 */
	private void sendRequestToServer() {
		// GetData obj =new GetData();
		// obj.execute("httP://google.com");

	}

	/**
	 * This method is called from GCMIntentService when the GCM message is
	 * received In this method we accessing the UIThread and modofying the
	 * TextView and toggling the button
	 * 
	 * @param message
	 *            GCM message
	 * @param state
	 * @param light
	 */
	public void callMethod(String message, String light, String state) {
		Log.i(TAG, message);
		final String mess = message;
		final String lightToToggle = light;
		final String newState = state;

		new Thread() {
			@Override
			public void run() {

				handler.post(new Runnable() {
					@Override
					public void run() {
						Log.i(TAG,
								"toggleButton was toggle due to received GCM");

						if (mess.equals(""))
							textView.setText(" " + mess.length() + " ");

						textView.setText(mess + " " + lightToToggle + " "
								+ newState);
						if (toggleButton.isChecked() == true){
							if (newState.equals("off"))
								toggleButton.toggle();
						}else if (newState.equals("on"))
							toggleButton.toggle();
					}
				});
			};
		}.start();

	}

	/**
	 * Trying to send some HTTP watch this video
	 * http://www.youtube.com/watch?v=f7U00_KXbHc
	 */
	/*
	 * public class GetData extends AsyncTask<String,Void,String>{
	 * 
	 * @Override protected String doInBackground(String... params) {
	 * 
	 * 
	 * BufferedReader reader=null; String data=null; try{
	 * 
	 * HttpClient client =new DefaultHttpClient();
	 * 
	 * URI uri=new URI(params[0]);
	 * 
	 * HttpGet get=new HttpGet(uri);
	 * 
	 * 
	 * HttpResponse response=client.execute(get);
	 * 
	 * }catch(){
	 * 
	 * }
	 * 
	 * return null; }
	 * 
	 * }
	 */

}