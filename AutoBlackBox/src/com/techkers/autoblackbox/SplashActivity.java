package com.techkers.autoblackbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity implements Runnable {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler handler = new Handler();
        handler.postDelayed(this, 3000);
	}

	public void run(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
