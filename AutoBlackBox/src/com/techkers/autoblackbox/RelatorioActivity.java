package com.techkers.autoblackbox;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RelatorioActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relatorio);
		
		Bundle bundle = getIntent().getExtras();
		preencherForca(bundle);
		preencherHora(bundle);
	}

	private void preencherHora(Bundle bundle) {
		if(bundle.getString("horaAcidente") != null){
			TextView horaAcidente = (TextView) findViewById(R.id.horaAcidente);
			final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			horaAcidente.setText(sdf.format(new Date(Long.parseLong(bundle.getString("horaAcidente")))));
		}
	}

	private void preencherForca(Bundle bundle) {
		if(bundle.getString("forcaAcidente") != null){
			TextView forcaAcidente = (TextView) findViewById(R.id.forcaAcidente);
			forcaAcidente.setText(bundle.getString("forcaAcidente"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
