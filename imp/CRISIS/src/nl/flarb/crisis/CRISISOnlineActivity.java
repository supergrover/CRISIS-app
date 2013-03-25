package nl.flarb.crisis;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public abstract class CRISISOnlineActivity extends Activity {
	public final static int RC_DISCONNECTED = 1;
	
	public void onDisconnectClicked(View v)
	{
		Toast.makeText(this, "Disconnect", Toast.LENGTH_LONG).show();
		setResult(RC_DISCONNECTED);
		super.finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == RC_DISCONNECTED) {
			setResult(RC_DISCONNECTED);
			super.finish();
		}
	}
}
