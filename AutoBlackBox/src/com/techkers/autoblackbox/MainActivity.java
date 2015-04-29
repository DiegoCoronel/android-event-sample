package com.techkers.autoblackbox;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.techkers.autoblackbox.events.Colisao;
import com.techkers.autoblackbox.events.Colisao.ColisaoCallback;

public class MainActivity extends Activity {

	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new Colisao(29, new ColisaoCallback() {
        	
        	private double forcaMaxima;
        	
			@Override
			public void colisaoOcorrida(int forcaDaColisao) {
				((TextView)findViewById(R.id.colisao)).setText("Valor da força: " + forcaDaColisao);
			}

			@Override
			public void forcaAtual(int forcaAtual) {
				if(forcaMaxima < forcaAtual) {
					forcaMaxima = forcaAtual;
					((TextView)findViewById(R.id.forcaMaxima)).setText("Valor da força atual: " + forcaMaxima);
				}
				((TextView)findViewById(R.id.forcaAtual)).setText("Valor da força máxima: " + forcaAtual);
			}
		} ), mSensor, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
