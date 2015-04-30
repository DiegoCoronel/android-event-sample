package com.techkers.autoblackbox;

import com.techkers.autoblackbox.services.SensorService;

import android.app.Application;
import android.content.Intent;

public class AutoBlackBoxApplication extends Application { 

	@Override
	public void onCreate() {
		super.onCreate();
		
		Intent intent = new Intent(this, SensorService.class);
		startService(intent);
	}
	
}
