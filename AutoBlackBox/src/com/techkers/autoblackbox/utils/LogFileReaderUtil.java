package com.techkers.autoblackbox.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;

import com.techkers.autoblackbox.utils.ForcaUtil;
import com.techkers.autoblackbox.vo.Evento;

public final class LogFileReaderUtil {
	
	private LogFileReaderUtil() {
		//do nothing
	}

	public static List<Evento> acessaInternalStorage(final Context contexto) {
		 
		String line;
		BufferedReader br = null;
		List<Evento> eventos = new ArrayList<Evento>();

		try {
			// Acessa o arquivo.
			br = new BufferedReader(new FileReader(contexto.getFilesDir().getPath()+"/log_acelerometro.txt"));
			
			// Faz a leitura, uma linha por vez, até o fim do arquivo,
			// gerando um string com todo o conteúdo separado por linha.
			while ((line = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(";");
				
				Evento evento = new Evento();
				evento.setForca( ForcaUtil.calcularForca(
						Float.valueOf(tokens.nextToken()),
						Float.valueOf(tokens.nextToken()),
						Float.valueOf(tokens.nextToken())
				));
				
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(Long.parseLong(tokens.nextToken()));
				
				evento.setData(calendar);
				
				eventos.add(evento);
			}
 
		} catch (Exception e) {
			Log.e("Erros", "Erro ao ler arquivo do Internal Storage", e);
		} finally {
			if (br != null) {
				try {br.close();}
				catch(Exception e){}
			}
		}
 
		// Retorna o conteúdo do arquivo.
		return eventos;
	}	
	
}
