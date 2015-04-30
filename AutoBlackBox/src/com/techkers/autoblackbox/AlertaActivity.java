package com.techkers.autoblackbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlertaActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);
		
		final TextView contador = (TextView) findViewById(R.id.contador);
		
		new CountDownTimer(30000, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 long segundos = millisUntilFinished / 1000;
		         contador.setText("00:" + ((segundos) < 10 ? "0" + segundos : segundos));
		     }

		     public void onFinish() {
		         contador.setText("00:00");
		     }
		  }.start();
		 
		  final Button cancelar = (Button) findViewById(R.id.botaoCancelar);
		  
		  cancelar.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	AlertaActivity.this.finish();
			    }
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
