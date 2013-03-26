package nl.flarb.crisis.programs;

import nl.flarb.crisis.CRISISOnlineActivity;
import nl.flarb.crisis.R;
import nl.flarb.crisis.R.layout;
import nl.flarb.crisis.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ProgramTourActivity extends CRISISOnlineActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_program_tour);
		
	}
}
