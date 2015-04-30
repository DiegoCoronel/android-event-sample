package com.techkers.autoblackbox.broadcasts;

import android.content.Context;
import android.content.SharedPreferences;

import com.techkers.autoblackbox.utils.ContatoUtil;
import com.techkers.autoblackbox.utils.SmsUtil;

public class SendMessageBroadcast {

	public static final String ACTION_SEND_MESSAGE = "autoblackbox.events.SendMessageBroadcast";
	
    private static final String CONTATO4 = "contato4";

	private static final String CONTATO3 = "contato3";

	private static final String CONTATO5 = "contato5";

	private static final String CONTATO6 = "contato6";

	private static final String CONTATO2 = "contato2";

	private static final String CONTATO1 = "contato1";

	private static final String PREF_OBS_GERAL = "PrefObsGeral";

	private static final String PREF_ALERGIA = "PrefAlergia";

	private static final String PREF_TIPO_SANG = "PrefTipoSang";

	public static final String PREFS_INFO_PESSOAL = "info_pessoais";
	
	public void enviarAlerta(Context context) {
		enviarParaContato(context, CONTATO1);
		enviarParaContato(context, CONTATO2);
		enviarParaContato(context, CONTATO3);
		enviarParaContato(context, CONTATO4);
		enviarParaContato(context, CONTATO5);
		enviarParaContato(context, CONTATO6);
	} 

	private void enviarParaContato(Context context, String contatoID) {
		String contato = recuperarValorContatos(context, contatoID);
		if (!contato.equals("")) {
			String numero = ContatoUtil.getTelefone(context, contato);
			String mensagem = "Aviso de acidente: Talvez eu tenha sofrido um acidente. Por favor tente me contactar, caso não consiga, chame socorro. Seguem minhas informações: Tipo Sanguíneo "
					+ recuperarValorContatos(context, PREF_TIPO_SANG) + " - Alergia: " + recuperarValorContatos(context, PREF_ALERGIA) + " - Outras info.: " + recuperarValorContatos(context, PREF_OBS_GERAL);
			SmsUtil.enviar(numero, mensagem);			
		}
	}
	
	public String recuperarValorContatos(Context context, String chave) {
        //Restaura as preferencias gravadas
        SharedPreferences settings = context.getSharedPreferences(PREFS_INFO_PESSOAL, 0);
		String valor = settings.getString(chave, "");
		return valor;
	}
	
}
