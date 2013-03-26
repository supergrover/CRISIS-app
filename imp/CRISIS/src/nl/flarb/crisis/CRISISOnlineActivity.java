package nl.flarb.crisis;

import android.content.Intent;
import android.view.View;

public abstract class CRISISOnlineActivity extends CRISISActivity {
	public final static int RC_DISCONNECTED = 1;
	
	public void onDisconnectClicked(View v)
	{
		setResult(RC_DISCONNECTED);
		super.finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RC_DISCONNECTED) {
			setResult(RC_DISCONNECTED);
			super.finish();
		}
	}
}
