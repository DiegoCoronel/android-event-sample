package com.techkers.autoblackbox.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.techkers.autoblackbox.broadcasts.ColisaoBroadcast;
import com.techkers.autoblackbox.events.Colisao;
import com.techkers.autoblackbox.events.Colisao.ColisaoCallback;
import com.techkers.autoblackbox.events.LogDoAcelerometro;

public class SensorService extends IntentService {

	private SensorManager mSensorManager;
	private Sensor mSensor;

	private long ultimoEvento;
	
	public SensorService() {
		super("SensorService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mSensorManager.registerListener(new LogDoAcelerometro(this), mSensor, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(new Colisao(new ColisaoCallbackImpl(this)), mSensor, SensorManager.SENSOR_DELAY_UI);
	}

	public class ColisaoCallbackImpl implements ColisaoCallback {

		private SensorService service;

		public ColisaoCallbackImpl(final SensorService service) {
			this.service = service;
		}

		@Override
		public void colisaoOcorrida(int forcaDaColisao) {
			final long ESPERA_ENTRE_EVENTOS = 5000; 
			if(ultimoEvento +  ESPERA_ENTRE_EVENTOS < System.currentTimeMillis()) {
				Intent intent = new Intent(ColisaoBroadcast.ACTION_COLISAO);
				service.sendBroadcast(intent);
				ultimoEvento = System.currentTimeMillis();
			}
		}
	}

}
