package nl.flarb.crisis.communication;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ConnectionServiceConnector implements ServiceConnection {
	public ConnectionService service = null;
	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) 
	{
		service = ((ConnectionService.ConnectionServiceBinder)binder).getService();
		connected();
	}

	@Override
	public void onServiceDisconnected(ComponentName arg0) {
		service = null;
		disconnected();
	}

	public void connected()
	{
	}
	public void disconnected()
	{
	}
}
