package com.techkers.autoblackbox;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

public class InfoPessoalActivity extends Activity implements OnClickListener {

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
	
	private static final int ACTION_PICK_CONTACTS = 0;
	
	private EditText edtTpSanguineo;
	private EditText edtAlergias;
	private EditText edtObsGeral;
	
	private ImageView imgFoto1;
	private ImageView imgFoto2;
	private ImageView imgFoto3;
	private ImageView imgFoto4;
	private ImageView imgFoto5;
	private ImageView imgFoto6;
	
	private Integer contatoSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_pessoal);
		
        this.edtTpSanguineo = (EditText) findViewById(R.id.edtTpSanguineo);
        this.edtAlergias = (EditText) findViewById(R.id.edtAlergias);
        this.edtObsGeral = (EditText) findViewById(R.id.edtObsGeral);
        
		this.recuperarValor(PREF_TIPO_SANG, edtTpSanguineo);
		this.recuperarValor(PREF_ALERGIA, edtAlergias);
		this.recuperarValor(PREF_OBS_GERAL, edtObsGeral);
        
        edtTpSanguineo.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus) {
					gravarValor(PREF_TIPO_SANG, edtTpSanguineo.getText().toString());
		        }				
			}
		});
        
        edtAlergias.setOnFocusChangeListener(new OnFocusChangeListener() {
        	@Override
        	public void onFocusChange(View v, boolean hasFocus) {
        		if(!hasFocus) {
        			gravarValor(PREF_ALERGIA, edtAlergias.getText().toString());
        		}				
        	}
        });
        
        edtObsGeral.setOnFocusChangeListener(new OnFocusChangeListener() {
        	@Override
        	public void onFocusChange(View v, boolean hasFocus) {
        		if(!hasFocus) {
        			gravarValor(PREF_OBS_GERAL, edtObsGeral.getText().toString());
        		}				
        	}
        });
        
		this.imgFoto1 = (ImageView) findViewById(R.id.imgFoto1);
		this.imgFoto2 = (ImageView) findViewById(R.id.imgFoto2);
		this.imgFoto3 = (ImageView) findViewById(R.id.imgFoto3);
		this.imgFoto4 = (ImageView) findViewById(R.id.imgFoto4);
		this.imgFoto5 = (ImageView) findViewById(R.id.imgFoto5);
		this.imgFoto6 = (ImageView) findViewById(R.id.imgFoto6);
		
		recuperarValorContato(CONTATO1, imgFoto1);
		recuperarValorContato(CONTATO2, imgFoto2);		
		recuperarValorContato(CONTATO3, imgFoto3);
		recuperarValorContato(CONTATO4, imgFoto4);
		recuperarValorContato(CONTATO5, imgFoto5);
		recuperarValorContato(CONTATO6, imgFoto6);
		
		this.imgFoto1.setOnClickListener(this);
		this.imgFoto2.setOnClickListener(this);
		this.imgFoto3.setOnClickListener(this);
		this.imgFoto4.setOnClickListener(this);
		this.imgFoto5.setOnClickListener(this);
		this.imgFoto6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		this.contatoSelecionado = v.getId();
		Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people/"));
        startActivityForResult(intent, ACTION_PICK_CONTACTS);
	}
	
	public void recuperarValor(String chave, EditText campo) {
        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences(PREFS_INFO_PESSOAL, 0);
		String valor = settings.getString(chave, "");
		campo.setText(valor);
	}
	
	public void recuperarValorContato(String chave, ImageView campo) {
        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences(PREFS_INFO_PESSOAL, 0);
		String valor = settings.getString(chave, "");
		if (!valor.equals("")) {
			final ContentResolver cr = getContentResolver();
			Bitmap bitFoto = getPhoto(cr, Long.valueOf(valor));
			campo.setImageBitmap(bitFoto);
		}
	}
	
	public String recuperarValorContatos(String chave) {
        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences(PREFS_INFO_PESSOAL, 0);
		String valor = settings.getString(chave, "");
		return valor;
	}
	
	public void gravarValor(String chave, String valor) {
		SharedPreferences settings = getSharedPreferences(PREFS_INFO_PESSOAL, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(chave, valor);
		// Confirma a gravação dos dados
		editor.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTION_PICK_CONTACTS) {
			if (resultCode == RESULT_OK) {
				Uri contactData = data.getData();
				final ContentResolver cr = getContentResolver();
				final Cursor c = cr.query(contactData, null, null, null, null);
				
				if (c.moveToFirst()) {
					final Long id = c.getLong(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					Bitmap bitFoto = getPhoto(cr, id);
					switch (contatoSelecionado) {
					case R.id.imgFoto1:
						imgFoto1.setImageBitmap(bitFoto);
						gravarValor(CONTATO1, id.toString());
						break;
					case R.id.imgFoto2:
						imgFoto2.setImageBitmap(bitFoto);
						gravarValor(CONTATO2, id.toString());
						break;
					case R.id.imgFoto3:
						imgFoto3.setImageBitmap(bitFoto);
						gravarValor(CONTATO3, id.toString());
						break;
					case R.id.imgFoto4:
						imgFoto4.setImageBitmap(bitFoto);
						gravarValor(CONTATO4, id.toString());
						break;
					case R.id.imgFoto5:
						imgFoto5.setImageBitmap(bitFoto);
						gravarValor(CONTATO5, id.toString());
						break;
					case R.id.imgFoto6:
						imgFoto6.setImageBitmap(bitFoto);
						gravarValor(CONTATO6, id.toString());
						break;
					}
				}
			}
		}
	}
	
	public Bitmap getPhoto(ContentResolver contentResolver, Long contactId) {
	    Uri contactPhotoUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);

	    // contactPhotoUri --> content://com.android.contacts/contacts/1557
	    InputStream photoDataStream = Contacts.openContactPhotoInputStream(contentResolver,contactPhotoUri);
	    Bitmap photo = BitmapFactory.decodeStream(photoDataStream);
	    return photo;
	}
	
}
