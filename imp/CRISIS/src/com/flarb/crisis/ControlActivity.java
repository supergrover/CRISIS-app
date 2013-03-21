package com.flarb.crisis;

import com.flarb.crisis.communication.Commands.Command;
import com.flarb.crisis.programs.ProgramNavigate;
import com.flarb.crisis.programs.ProgramTour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ControlActivity extends Activity {

	Button b_disconnect = null;
	Button b_shutdown = null;
	
	Button b_tour = null;
	Button b_navigate = null;
	Button b_inspect = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		final Activity self = this;
		
		b_disconnect = (Button)findViewById(R.id.button_disconnect);
		b_shutdown = (Button)findViewById(R.id.button_shutdown);
		
		b_tour = (Button)findViewById(R.id.button_tour);
		b_navigate = (Button)findViewById(R.id.button_navigate);
		b_inspect = (Button)findViewById(R.id.button_inspect);
		
		b_disconnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Global.disconnect();
				finish(); // Return to previous activity
			}
		});
		
		b_shutdown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Command c = Command.newBuilder()
						.setCommand(Command.CommandType.Shutdown)
						.build();
				Global.sendCommand(c);
			}
		});
		
		b_tour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(self, ProgramTour.class));
			}
		});
		b_navigate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(self, ProgramNavigate.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

}
