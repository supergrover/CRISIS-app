package nl.flarb.crisis;

import nl.flarb.crisis.communication.ConnectionService;
import nl.flarb.crisis.communication.ConnectionServiceConnector;
import android.content.Intent;
import android.view.View;

public abstract class CRISISOnlineActivity extends CRISISActivity {
	public final static int RC_DISCONNECTED = 1;
	public final static int RC_OK = 2;
	
	public void onDisconnectClicked(View v)
	{
		ConnectionServiceConnector conn = new ConnectionServiceConnector() {
			@Override
			protected void connected(ConnectionService service)
			{
				service.disconnect();
				CRISISOnlineActivity.this.unbindService(this);
			}
		};
		bindService(new Intent(this, ConnectionService.class), conn, 0);
		
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
