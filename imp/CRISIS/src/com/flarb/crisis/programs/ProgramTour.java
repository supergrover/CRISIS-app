package com.flarb.crisis.programs;

import com.flarb.crisis.Global;
import com.flarb.crisis.R;
import com.flarb.crisis.communication.Commands.Command;
import com.flarb.crisis.communication.Commands.Program;
import com.flarb.crisis.communication.Commands.Program.Step;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ProgramTour extends Activity {

	Button b_start_program;
	RadioButton b_direction_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_tour);
		
		b_start_program = (Button)findViewById(R.id.program_tour_start_program);
		b_direction_left = (RadioButton)findViewById(R.id.program_tour_first_left);
		
		b_start_program.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Command c = Command.newBuilder()
					.setCommand(Command.CommandType.StartProgram)
					.setProgram(
							Program.newBuilder()
								.setType(Program.ProgramType.Tour)
								.addSteps(Program.Step.newBuilder()
										.setDirection((b_direction_left.isChecked()) ? Program.Direction.Left : Program.Direction.Right)
										.setNth(1)
										.build())
								.build())
					.build();
				Global.sendCommand(c);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.program_tour, menu);
		return true;
	}

}
