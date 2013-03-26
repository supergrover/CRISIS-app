package nl.flarb.crisis.programs;

import nl.flarb.crisis.CRISISOnlineActivity;
import nl.flarb.crisis.R;
import nl.flarb.crisis.R.id;
import nl.flarb.crisis.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ProgramActivity extends CRISISOnlineActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_program);
		
		((Button)findViewById(R.id.program_new)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(ProgramActivity.this, ProgramNewActivity.class), 0);
			}
		});
	}

}
