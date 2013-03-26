package nl.flarb.crisis;

import nl.flarb.crisis.R;
import nl.flarb.crisis.programs.ProgramActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class OverviewActivity extends CRISISOnlineActivity {
	private CRISISEnvironmentDisplay display = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_overview);
        setTitle("Overview");
        
        display = (CRISISEnvironmentDisplay)findViewById(R.id.environment_display);
        ((Button)findViewById(R.id.overview_configure_task)).setOnClickListener(
        		new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(OverviewActivity.this, ProgramActivity.class);
						startActivityForResult(i, 0);
					}
				});
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		display.onStart();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		display.onStop();
	}
}
