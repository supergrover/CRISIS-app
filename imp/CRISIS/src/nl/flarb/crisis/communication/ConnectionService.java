package nl.flarb.crisis.communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import nl.flarb.crisis.OverviewActivity;
import nl.flarb.crisis.R;
import nl.flarb.crisis.communication.Commands.Command;
import nl.flarb.crisis.communication.Commands.Header;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ConnectionService extends Service {
	public static final String CONNECTED = "nl.flarb.crisis.ConnectionService.CONNECTED";
	public static final String CONNECT_FAILED = "nl.flarb.crisis.ConnectionService.CONNECT_FAILED";
	public static final String WHY = "nl.flarb.crisis.ConnectionService.WHY";
	
	public static Boolean bind(Context ctx, ConnectionServiceConnector conn)
    {
    	return ctx.bindService(new Intent(ctx, ConnectionService.class), conn, 0);
    }
	public static void unbind(Context ctx, ConnectionServiceConnector conn)
	{
		ctx.unbindService(conn);
	}
	
	
	public class ConnectionServiceBinder extends Binder
	{
		ConnectionService getService()
		{
			return ConnectionService.this;
		}
	}
	private final IBinder _binder = new ConnectionServiceBinder();
	@Override
	public IBinder onBind(Intent intent) {
		return _binder;
	}
	
	@Override
	public void onCreate()
	{
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		showNotification();
	}
	
	@Override
	public void onDestroy()
	{
		_is_destroying = true;
		disconnect();
		nm.cancel(R.string.online_notification);
		_is_destroying = false;
	}
	
	@Override
	public void onStart(Intent intent, int startid)
	{
		if(intent == null) {
			stopSelf();
		}
		_connect(intent.getExtras().getString("host"), intent.getExtras().getInt("port"));
	}
	

	private void showNotification()
	{
		PendingIntent content = PendingIntent.getActivity(this, 0,
				new Intent(this, OverviewActivity.class), 0);
		notification_builder = new NotificationCompat.Builder(this);
		notification_builder
			.setSmallIcon(R.drawable.ic_launcher)
			.setWhen(System.currentTimeMillis())
			.setAutoCancel(false)
			.setContentIntent(content);
		_set_notification_text("CRISIS");
	}
	
	private void _set_notification_text(String msg)
	{
		Notification nf = notification_builder
			.setContentText(msg)
			.setContentTitle(msg)
			.build();
		nm.notify(R.string.online_notification, nf);
		
	}
	
	
	public Socket socket = null;
	public String host = "";
	public int port = 0;
	
	private Boolean _is_destroying = false;
	
	private NotificationManager nm;
	private NotificationCompat.Builder notification_builder = null;
	
	private class ConnectionThread extends Thread
	{
		public Boolean running = true;
		
		public void doStop()
		{
			running = false;
		}
		@Override
		public void run()
		{
			socket = new Socket();
			_set_notification_text("CRISIS Connecting..");
			try {
				socket.connect(new InetSocketAddress(ConnectionService.this.host, 
													 ConnectionService.this.port));
				socket.setSoTimeout(1000);
			} catch(IOException e) {
				Intent cf = new Intent(CONNECT_FAILED);
				cf.putExtra(WHY, e.getMessage());
				sendBroadcast(cf);
				disconnect();
				return;
			}
			_set_notification_text("CRISIS Connected");
			sendBroadcast(new Intent(CONNECTED));

			byte[] h_buf = new byte[5];
			byte[] buffer;
			while(running) {
				try {
					if(socket.getInputStream().read(h_buf, 0, 5) == -1) {
						break;
					}
					Header.Builder hbuilder = Header.newBuilder();
					hbuilder.mergeFrom(h_buf);
					Header hdr = hbuilder.build();
					
					buffer = new byte[hdr.getSize()];
					if(socket.getInputStream().read(buffer, 0, hdr.getSize()) == -1) {
						break;
					};
					Command.Builder builder = Command.newBuilder();
					builder.mergeFrom(buffer);
					Command cmd = builder.build();
					_received_command(cmd);
				} catch (IOException e) {
					// Timeout
				}
			}
		}
	};
	private ConnectionThread _connection_thread = null;
	
	private void _connect(String host, int port)
	{
		this.host = host;
		this.port = port;
		
		if(_connection_thread != null) {
			_connection_thread.doStop();
		}
		_connection_thread = new ConnectionThread();
		_connection_thread.start();
	}
	
	public void disconnect()
	{	
		if(_connection_thread != null) {
			_connection_thread.doStop();
		}
		if(socket != null) {
			try {
				socket.close();
			} catch(IOException e) {
				// pass
			}
		}
		if(_connection_thread != null) {
			try {
				_connection_thread.join();
			} catch(InterruptedException e) {
				// pass
			}
		}
		_connection_thread = null;
		socket = null;
		if(!_is_destroying) {
			stopSelf();
		}
	}
	
	private void _received_command(Command cmd)
	{
		
	}

	public void sendCommand(String c)
	{
		Toast.makeText(this, c, Toast.LENGTH_LONG*3).show();
	}
}
