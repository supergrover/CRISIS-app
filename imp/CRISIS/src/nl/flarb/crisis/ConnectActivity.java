
package nl.flarb.crisis;

import java.util.regex.Pattern;

import nl.flarb.crisis.R;
import nl.flarb.crisis.communication.ConnectionService;
import nl.flarb.crisis.communication.ConnectionServiceConnector;


import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ConnectActivity extends CRISISActivity {
	public static String CONNECT_PREFS = "ConnectPrefs";
	public static String CONNECT_PREF_IP = "IP";

	private static final int _default_port = 1337;
	
	private Button b_connect;
	private EditText ip;
	
	private PopupWindow connecting_popup;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_connect);
        setTitle("Connect");

        LayoutInflater inf = (LayoutInflater)
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        connecting_popup = new PopupWindow(
        		inf.inflate(R.layout.popup_connecting, null, false),
        		100, 100,
        		true);
		
        
        b_connect = (Button)findViewById(R.id.connect_button);
        ip = (EditText)findViewById(R.id.connect_ip);
        
        final SharedPreferences settings = getSharedPreferences(CONNECT_PREFS, 0);
        ip.setText(settings.getString(CONNECT_PREF_IP, ""));
        
        b_connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isValidIP(ip.getText().toString())) {
					SharedPreferences.Editor editor = settings.edit();
					editor.putString(CONNECT_PREF_IP, ip.getText().toString());
					editor.commit();
					
					connecting_popup.showAtLocation(findViewById(R.id.connect_root), Gravity.CENTER, 0, 0);
				
					_connect(ip.getText().toString(), _default_port);
				} else {
					ip.setError("Invalid IP address");
				}
			}
        });
        
        registerReceiver(_connected, new IntentFilter(ConnectionService.CONNECTED));
        registerReceiver(_connect_failed, new IntentFilter(ConnectionService.CONNECT_FAILED));
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	unregisterReceiver(_connected);
    	unregisterReceiver(_connect_failed);
    	ConnectionService.unbind(this, conn);
    }
    
    private BroadcastReceiver _connected = new BroadcastReceiver()
    {
		@Override
		public void onReceive(Context ctx, Intent intent)
		{
			Intent si = new Intent(ConnectActivity.this, OverviewActivity.class);
			startActivity(si);
			connecting_popup.dismiss();
		}
    };
    
    private BroadcastReceiver _connect_failed = new BroadcastReceiver()
    {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(ConnectActivity.this, 
					String.format("Connect failed:\n%s", intent.getExtras().getString(ConnectionService.WHY)),
					Toast.LENGTH_LONG*2).show();
			connecting_popup.dismiss();
		}
    	
    };
    
    private void _connect(String host, int port)
    {
    	Intent ci = new Intent(ConnectActivity.this, ConnectionService.class);
    	ci.putExtra("host", host);
    	ci.putExtra("port", port);
    	startService(ci);
        ConnectionService.bind(this, conn);
    }
    
    private Boolean isValidIP(String s)
    {
    	return Pattern.matches("([0-9]{1,3}[.]){3}([0-9]{1,3})", s);
    }

    private ConnectionServiceConnector conn = new ConnectionServiceConnector();
    
    @Override
	public void onRestart()
	{
    	super.onRestart();
    	if(conn.service != null) {
    		conn.service.disconnect();
    	}
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
