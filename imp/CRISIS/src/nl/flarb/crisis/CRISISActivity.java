package nl.flarb.crisis;

import android.app.Activity;
import android.content.Intent;

public class CRISISActivity extends Activity 
{
	@Override
	protected void onPause()
	{
		super.onPause();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
