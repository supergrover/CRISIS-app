package nl.flarb.crisis.programs;

import nl.flarb.crisis.CRISISOnlineActivity;

import nl.flarb.crisis.R;
import nl.flarb.crisis.R.id;
import nl.flarb.crisis.R.layout;
import nl.flarb.crisis.R.menu;
import nl.flarb.crisis.communication.Commands.Command;
import nl.flarb.crisis.communication.Commands.Program;
import nl.flarb.crisis.communication.ConnectionService;
import nl.flarb.crisis.communication.ConnectionServiceConnector;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ProgramTourActivity extends CRISISOnlineActivity {
	private RadioButton first_left = null;
	private ConnectionServiceConnector _conn = new ConnectionServiceConnector();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_program_tour);
		
		first_left = (RadioButton)findViewById(R.id.program_tour_first_left);
		ConnectionService.bind(this,_conn);
		
		((Button)findViewById(R.id.button_program_tour_start)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				if(_conn.service == null) {
					Toast.makeText(ProgramTourActivity.this, 
							"Failed to start program: Unable to contact CRISIS", 
							Toast.LENGTH_LONG*3).show();
					return;
				}				
				Command cmd = Command.newBuilder()
						.setType(Command.Type.StartProgram)
						.setProgram(Program.newBuilder()
								.setType(Program.ProgramType.Tour)
								.addSteps(Program.Step.newBuilder()
										.setNth(1)
										.setDirection((first_left.isChecked()) ? Program.Direction.Left : Program.Direction.Right)
										.build())
								.build())
						.build();
				_conn.service.sendCommand(cmd);
				setResult(RC_OK);
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ConnectionService.unbind(this, _conn);
	}
}
