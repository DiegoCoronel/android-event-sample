package com.techkers.autoblackbox.events;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Colisao implements SensorEventListener {

	private static final int GRAVIDADE = 9;
	
	private final double forcaDeImpacto;
	private final ColisaoCallback callback;
	
	public Colisao(double forcaDeImpacto, ColisaoCallback callback) {
		this.forcaDeImpacto = forcaDeImpacto;
		this.callback = callback;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		final float x = event.values[0];//x
		final float y = event.values[1];//y
		final float z = event.values[2];//z
		
		int forca = (int)Math.sqrt((x*x + y*y + z*z));
		
		int forcaGerada = forca - GRAVIDADE;
		
		if(forcaGerada > forcaDeImpacto) {
			callback.colisaoOcorrida(forcaGerada);
		}
		callback.forcaAtual(forcaGerada);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	public static interface ColisaoCallback {
		void colisaoOcorrida(int forcaDaColisao);
		void forcaAtual(int forcaAtual);
	}
	
}
