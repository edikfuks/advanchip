package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	private Button loginButton;
	private EditText idEditText;
	private EditText pwEditText;
	public static String regId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        if(savedInstanceState == null){
        	GCMRegistrar.checkDevice(this);
        	GCMRegistrar.checkManifest(this);
        	GCMRegistrar.register(MainActivity.this, GCMIntentService.SENDER_ID);
        }
		
		loginButton = (Button)findViewById(R.id.login_button);
		idEditText = (EditText)findViewById(R.id.id_edittext);
		pwEditText = (EditText)findViewById(R.id.pw_edittext);
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LightActivity.class);
				startActivity(intent);
			}
		});
		
		idEditText.setOnKeyListener(new OnKeyListener() {
		    @Override
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if(keyCode == event.KEYCODE_ENTER) return true;
		        return false;
		    }
		});
		
		pwEditText.setOnKeyListener(new OnKeyListener() {
		    @Override
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if(keyCode == event.KEYCODE_ENTER) return true;
		        return false;
		    }
		});	
	}
}