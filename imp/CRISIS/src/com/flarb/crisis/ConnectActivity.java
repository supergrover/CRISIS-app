package com.flarb.crisis;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ConnectActivity extends Activity {

    private Button b_connect = null;
    private View progress = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        
        b_connect = (Button)findViewById(R.id.connect_button);
        progress = (View)findViewById(R.id.connect_progress);  
        

        // For the intent
        final ConnectActivity self = this;
        
        b_connect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
        		set_connecting(true);
        		
				String ip = ((TextView)findViewById(R.id.host_ip)).getText().toString();
				
				if(!valid_ip(ip)) {
					return;
				}
				
				Runnable success = new Runnable() {
					@Override
					public void run() 
					{
						runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								set_connecting(false);
								startActivity(new Intent(self, ControlActivity.class));
							}
						});
					}
				};
				
				Runnable error = new Runnable() {
					@Override
					public void run()
					{
						runOnUiThread(new Runnable() {
							@Override
							public void run()
							{
								Log.v("Connect", "Failed");
								set_connecting(false);
							}
						});
					}
				};
				
				Global.connect_async(ip, success, error);
			}
        });
    }

    private void set_connecting(Boolean connecting)
    {
        b_connect.setEnabled(!connecting);
		progress.setVisibility((connecting) ? View.VISIBLE : View.INVISIBLE);
		
    }
    
    private Boolean valid_ip(String c)
    {
    	return Pattern.matches("([0-9]{1,3}[.]){3}([0-9]{1,3})", c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
