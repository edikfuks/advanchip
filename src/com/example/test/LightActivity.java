package com.example.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ToggleButton;

public class LightActivity extends Activity {
    final Context context = this;
    private static final String[][] data = {{"LIVINGROOM","KITCHEN"}};
    private ExpandableListView expandableListView;
    
    private static Handler handler = new Handler();
    
    // Dialog
	private static ToggleButton switchButton;
	private static Button toggleButton;

	private Dialog switchDialog;
	private static String signal = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
      
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView1);
        expandableListView.setAdapter(new SampleExpandableListAdapter(context, this, data));
        expandableListView.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switchDialog = new Dialog(LightActivity.this, R.style.PauseDialog);
                switchDialog.setContentView(R.layout.dialog);
                                
                if(childPosition == 0){
                	switchDialog.setTitle("LIVINGROOM");
                	signal = "L";
                	//switchDialog
                }
                else if(childPosition == 1){
                	switchDialog.setTitle("KITCHEN");
                	signal = "K";
                }
                
                switchDialog.show();
                
                //switchButton = (ToggleButton)switchDialog.findViewById(R.id.switch_button);
                toggleButton=(Button)switchDialog.findViewById(R.id.button1);
                
                toggleButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					if(signal.charAt(0) == 'L'){
						if(toggleButton.getText().equals("on")) signal = "LN";
						else signal = "LF";
					}
					else if(signal.charAt(0) == 'K'){
						if(toggleButton.getText().equals("on")) signal = "KN";
						else signal = "KF";
					}
					
					new MyAsyncTask().execute(signal);
				
					
				}
			});
			
			
               /*  switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					// Send message to Server(Switch On)
                	
                	 @Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(signal.charAt(0) == 'L'){
							if(isChecked) signal = "LN";
							else signal = "LF";
						}
						else if(signal.charAt(0) == 'K'){
							if(isChecked) signal = "KN";
							else signal = "KF";
						}
						
						new MyAsyncTask().execute(signal);
					}
                });
                */
					
					
                
                return false;
			} 
			
			
			
        });
        
        
         
        
    }
    
    static void toggleButton(){
    	Log.i("Info", "the button was toggled");
    	
    	}
    
    static void updateTheView(String message,final String light, final String state) {
    	final String TAG="Info";
		Log.i("updateView", "the GCM has triggered the button");
		new Thread() {
			@Override
			public void run() {

				handler.post(new Runnable() {
					@Override
					public void run() {

						Log.i(TAG,
								"background method has started to toggle the button");
						if (signal.charAt(0) == 'L' && light.equals("1")) {
							if (toggleButton.getText().equals("on") && state.equals("off")) {
								signal = "LN";
								toggleButton.setText("off");
							} else {
								if (!(toggleButton.getText().equals("on"))
										&& state.equals("on")) {
									signal = "LF";
									toggleButton.setText("on");
								}
							}
						} else if (signal.charAt(0) == 'K') {
							if (toggleButton.getText().equals("on") && state.equals("off")) {
								signal = "KN";
								toggleButton.setText("off");
							} else {
								if (!(toggleButton.getText().equals("on"))
										&& state.equals("on")) {
									signal = "KF";
									toggleButton.setText("on");
								}
							}
						}
					}
				});
			};
		}.start();
	}
}