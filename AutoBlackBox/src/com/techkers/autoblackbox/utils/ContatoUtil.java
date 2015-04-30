package com.techkers.autoblackbox.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public final class ContatoUtil {

	private ContatoUtil() {
		//do nothing
	}
	
	public static final String getTelefone(final Context contexto, final String idContato) {
		ContentResolver cr = contexto.getContentResolver();
	    Cursor phones = cr.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + idContato, null, null);
	    String numero = "";
	    while (phones.moveToNext()) {
	        numero = phones.getString(phones.getColumnIndex(Phone.NUMBER));
	        break;
	    }
		phones.close();
		return numero;
	}
	
}
