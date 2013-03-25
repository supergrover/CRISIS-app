package nl.flarb.crisis;

import nl.flarb.crisis.R;

import android.os.Bundle;
import android.view.Window;

public class OverviewActivity extends CRISISOnlineActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_overview);
        setTitle("Overview");
	}
}
