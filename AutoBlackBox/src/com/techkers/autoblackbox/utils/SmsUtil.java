package com.techkers.autoblackbox.utils;

import android.telephony.SmsManager;


public final class SmsUtil {
	
	
	private SmsUtil() {
		//do nothing
	}
	
	public static void enviar(final String numero, final String mensagem) {
		String numeroAjustado = ajustarNumero(numero);
		SmsManager bat = SmsManager.getDefault();
        bat.sendTextMessage(numeroAjustado, null, mensagem, null, null);
	}

	private static String ajustarNumero(final String numero) {
		String numeroAjustado = numero.replaceAll("\\-", "");
		if(!numeroAjustado.contains("+")) {
			numeroAjustado = "+5592" + numeroAjustado;
		}
		return numeroAjustado;
	}

}
