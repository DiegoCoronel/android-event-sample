package com.techkers.autoblackbox.schedules;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public final class CollisionEventScheduler {

	public static void scheduler(int segundos, Context context) {
		// Intent para disparar o broadcast
		Intent it = new Intent("MUDAR");
		PendingIntent p = PendingIntent.getBroadcast(context, 0, it, 0);
	
		// Para executar o alarme depois de x segundos a partir de agora
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.SECOND, segundos);
	
		// Agenda o alarme
		AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		long time = c.getTimeInMillis();
		alarme.set(AlarmManager.RTC_WAKEUP, time, p);
	}
	
}
