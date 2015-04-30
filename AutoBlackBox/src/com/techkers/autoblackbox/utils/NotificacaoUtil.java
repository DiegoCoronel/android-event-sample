package com.techkers.autoblackbox.utils;

import com.techkers.autoblackbox.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

/**
 * Class to generate notifications.
 */
public final class NotificacaoUtil {

    /**
     * API Level 17 - Android 4.2 or higher
     */
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void create(Context context, String title, String message, int id, Intent intent) {

        // PendingIntent to perform the intent to select the notification
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

    	Notification n = null;
        
    	int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 11) {
	        Builder builder = new Builder(context);
	        builder.setContentTitle(title);
	        builder.setContentText(message);
	        builder.setSmallIcon(R.drawable.ic_launcher);
	        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
	        builder.setContentIntent(p);
	        
			if (apiLevel >= 17) {
				// Android 4.2
				n = builder.build();
			} else {
				// Android 3.x
				n = builder.getNotification();
			}
    	} else {
    	    // Android 2.2
    	    n = new Notification( R.drawable.ic_launcher, title, System.currentTimeMillis());

    	    // Informações
    	    n.setLatestEventInfo(context, title, message, p);
    	}
    	
    	// id ( unique number) that identifies this notification
    	NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
    	nm.notify(id, n);
    }

}
