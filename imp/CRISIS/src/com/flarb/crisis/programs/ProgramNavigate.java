package com.flarb.crisis.programs;

import java.util.ArrayList;

import com.flarb.crisis.R;
import com.flarb.crisis.communication.Commands.Program;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ProgramNavigate extends Activity {

	Button b_add_step;
	Button b_start_program;
	RadioButton step_direction_left;
	TextView step_nth;
	ListView steps;
	
	ArrayList<String> stored_step_strings = new ArrayList<String>();
	ArrayList<Program.Step> stored_steps = new ArrayList<Program.Step>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_navigate);
		
		b_start_program = (Button)findViewById(R.id.program_navigate_start_program);
		b_add_step = (Button)findViewById(R.id.button_add_step);
		step_direction_left = (RadioButton)findViewById(R.id.program_navigate_direction_left);
		step_nth = (TextView)findViewById(R.id.program_navigate_step_nth);
		steps = (ListView)findViewById(R.id.program_navigate_steps);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stored_step_strings);
		steps.setAdapter(adapter);
		
		b_add_step.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				int nth = Integer.parseInt(step_nth.getText().toString());
				stored_step_strings.add(String.format("%dth %s", nth, (step_direction_left.isChecked()) ? "Left" : "Right"));
				stored_steps.add(Program.Step.newBuilder()
						.setDirection((step_direction_left.isChecked()) ? Program.Direction.Left : Program.Direction.Right)
						.setNth(nth)
						.build());
				adapter.notifyDataSetChanged();
			}
		});
		
		b_start_program.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// TODO
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.program_navigate, menu);
		return true;
	}

}
