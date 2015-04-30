package com.techkers.autoblackbox.events;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.util.Log;

public class LogDoAcelerometro implements SensorEventListener {
	
	private final Context contexto;
	private long ultimoEvento;
	private static final long INTERVALO_EM_SEGUNDOS = 1000;
	private static final byte MAXIMO_DE_INTERVALOS = 10;
	private final String[] log;
	private byte posicaoAtualDoLog;

	public LogDoAcelerometro(final Context context) {
		this.contexto = context;
		this.log = new String[10];
		posicaoAtualDoLog = 0;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(ultimoEvento + INTERVALO_EM_SEGUNDOS < System.currentTimeMillis()) {
			ultimoEvento = System.currentTimeMillis();
			StringBuilder builder = new StringBuilder();
			builder.append((int)event.values[0])
					.append(";")
					.append((int)event.values[1])
					.append(";")
					.append((int)event.values[2])
					.append(";")
					.append(System.currentTimeMillis())
					.append("\n");
					
			log[posicaoAtualDoLog++ % MAXIMO_DE_INTERVALOS] = builder.toString();
			
			if(posicaoAtualDoLog == 10) {
				salvarExternalStorage(log);
				posicaoAtualDoLog = 0;
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	private void salvarExternalStorage(final String[] logs) {
		 
		// Obtém o estado do storage.
		final String mediaState = Environment.getExternalStorageState();
 
		// Testa se ele está disponível.
		if (mediaState.equals(Environment.MEDIA_MOUNTED)) {
 
			FileWriter fileWriter = null;
			try {
				// Cria o arquivo onde serão salvas as informações.
				File file = new File(contexto.getFilesDir().getPath()+"/log_acelerometro.txt");
				fileWriter = new FileWriter(file, true);
				for(String texto : logs) {
					fileWriter.append(texto);
				}
				// Escreve no arquivo.
				fileWriter.flush();
 
			} catch (final IOException e) {
				Log.e("Erros", "Erro ao salvar usando External Storage", e);
			} finally {
				// Fecha os recursos.
				if (fileWriter != null) {
					try {fileWriter.close();}
					catch(Exception e){}
				}
			}
		}
	}
}
