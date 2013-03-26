package nl.flarb.crisis.programs;

import nl.flarb.crisis.CRISISOnlineActivity;
import nl.flarb.crisis.R;
import nl.flarb.crisis.R.layout;
import nl.flarb.crisis.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ProgramNewActivity extends CRISISOnlineActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_program_new);
		
		((Button)findViewById(R.id.button_program_tour)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(ProgramNewActivity.this, ProgramTourActivity.class), 0);
			}
		});
		
		((Button)findViewById(R.id.button_program_navigate)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(ProgramNewActivity.this, ProgramNavigateActivity.class), 0);
			}
		});
		
		((Button)findViewById(R.id.button_program_find_damaged)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(ProgramNewActivity.this, ProgramFindDamagedActivity.class), 0);
			}
		});
	}
}
