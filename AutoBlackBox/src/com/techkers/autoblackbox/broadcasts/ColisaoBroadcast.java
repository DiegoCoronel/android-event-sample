package com.techkers.autoblackbox.broadcasts;

import com.techkers.autoblackbox.schedules.CollisionEventScheduler;
import com.techkers.autoblackbox.utils.NotificacaoUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ColisaoBroadcast extends BroadcastReceiver {

	public static final String ACTION_COLISAO = "autoblackbox.events.ColisaoBroadcast";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//NOTIFICAR
		NotificacaoUtil.create(context, "", "", 1, new Intent());
		
		//TOCAR
		
		//INICIAR ALARME
		CollisionEventScheduler.scheduler(20, context);
	}

}
