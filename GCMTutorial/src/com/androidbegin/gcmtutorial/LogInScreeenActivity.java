package com.androidbegin.gcmtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogInScreeenActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.homescreen);lmlmldcdcddcd
		
		
		
		
		
		
	}
	
	public void switchScreen(View view){
	
		Intent intent=new Intent(view.getContext(), GCMMainActivity.class);
		this.startActivity(intent);
		
	}
	
	
	

}
