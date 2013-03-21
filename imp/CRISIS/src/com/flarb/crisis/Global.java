package com.flarb.crisis;

import java.io.IOException;


import java.net.InetSocketAddress;
import java.net.Socket;
import android.util.Log;

import com.flarb.crisis.communication.Commands.Command;

public class Global {
	
	public static Socket connection = null;
	public static final int crisis_port = 1337;
	private static Thread connect_th = null;
	
	public static void connect_async(final String ip, final Runnable success, final Runnable error)
	{
		connect_th = new Thread() {
			@Override
			public void run()
			{
				try {
					connection = new Socket();
					connection.connect(new InetSocketAddress(ip, crisis_port));
					success.run();
				} catch(IOException e) {
					Log.e("Connecting", e.getMessage());
					error.run();
				}
			}
		};
		connect_th.start();
	}
	
	public static void disconnect()
	{
		if(connection != null) {
			try {
				connection.close();
			} catch(IOException e) {
				Log.v("Connection", String.format("Failed to disconnect: %s", e.getMessage()));
			}
			connection = null;
		}
	}
	
	public static void sendCommand(Command c)
	{
		if(connection != null) {
			try {
				c.writeTo(connection.getOutputStream());
			} catch(IOException e) {
				Log.v("Transmission", e.getMessage());
			}
		}
	}
}
