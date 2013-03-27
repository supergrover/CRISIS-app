package nl.flarb.crisis.programs;

import java.util.ArrayList;
import nl.flarb.crisis.CRISISOnlineActivity;
import nl.flarb.crisis.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import nl.flarb.crisis.communication.ConnectionService;
import nl.flarb.crisis.communication.ConnectionServiceConnector;
import nl.flarb.crisis.communication.Commands.Command;
import nl.flarb.crisis.communication.Commands.Program;

public class ProgramNavigateActivity extends CRISISOnlineActivity {

	private Button _up = null;
	private Button _down = null;
	private Button _del = null;
	private EditText _nth = null;
	private RadioButton _left = null;
	
	private ListView _items = null;
	
	private ArrayList<Program.Step> _steps = new ArrayList<Program.Step>();
	private ArrayList<String> _steps_str = new ArrayList<String>();
	private ArrayAdapter<String> _steps_adapter;
	
	
	private ConnectionServiceConnector _conn = new ConnectionServiceConnector();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_program_navigate);
		
		_up = (Button)findViewById(R.id.program_navigate_up);
		_down = (Button)findViewById(R.id.program_navigate_down);
		_del = (Button)findViewById(R.id.program_navigate_delete);
		_nth = (EditText)findViewById(R.id.program_navigate_nth);
		_left = (RadioButton)findViewById(R.id.program_navigate_left);
		_items = (ListView)findViewById(R.id.program_navigate_items);
		((Button)findViewById(R.id.program_navigate_add)).setOnClickListener(_add_item);
		((Button)findViewById(R.id.program_navigate_start_program)).setOnClickListener(_start_program);
		
		_steps_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				_steps_str);
		_items.setAdapter(_steps_adapter);
		
		ConnectionService.bind(this, _conn);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ConnectionService.unbind(this, _conn);
	}
	
	
	private View.OnClickListener _add_item = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			int nth = Integer.parseInt(_nth.getText().toString());
			String dir = (_left.isChecked() ? "Left" : "Right");
			_steps_str.add(String.format("%d%s %s", nth, (nth == 1) ? "st" : ((nth == 2) ? "nd" : "th"), dir));
			_steps.add(Program.Step.newBuilder()
					.setNth(nth)
					.setDirection((_left.isChecked()) ? Program.Direction.Left : Program.Direction.Right)
					.build());
			_steps_adapter.notifyDataSetChanged();
			
			_nth.setText("");
			_left.setChecked(true);
		}
	};
	
	private View.OnClickListener _start_program = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			Program.Builder b = Program.newBuilder()
				.setType(Program.ProgramType.Navigate);
			for(Program.Step s: _steps) {
				b.addSteps(s);
			}
	
			Command cmd = Command.newBuilder()
					.setType(Command.Type.StartProgram)
					.setProgram(b)
					.build();
			_conn.service.sendCommand(cmd);
			setResult(RC_OK);
			finish();
		}
	};
}
