package nl.flarb.crisis;
import nl.flarb.crisis.communication.Commands;
import nl.flarb.crisis.communication.Commands.Environment;
import nl.flarb.crisis.communication.ConnectionService;
import nl.flarb.crisis.communication.ConnectionServiceConnector;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;



public class CRISISEnvironmentDisplay extends SurfaceView {
	private Paint paint;
	private Bitmap sunflower_bitmap;
	private Bitmap crisis_bitmap;
	
	private final static int sunflower_size = 20;
	private final static int crisis_size = 15;
	
	private ConnectionServiceConnector _conn = new ConnectionServiceConnector();
	
	private void init(Context context)
	{
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		setWillNotDraw(false);
		
		sunflower_bitmap = BitmapFactory.decodeResource(
				getResources(), 
				R.drawable.sunflower);
		crisis_bitmap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.crisis);

	}
	

	public CRISISEnvironmentDisplay(Context context)
	{
		super(context);
		init(context);
	}	
	
	public CRISISEnvironmentDisplay(Context context, AttributeSet attr)
	{
		super(context, attr);
		init(context);
	}
	
	public CRISISEnvironmentDisplay(Context context, AttributeSet attr, int defStyle)
	{
		super(context, attr, defStyle);
		init(context);
	}
	
	public void onStart()
	{
		ConnectionService.bind(getContext(), _conn);
		getContext().registerReceiver(_environment_changed, 
				new IntentFilter(ConnectionService.ENVIRONMENT_CHANGED));
	}
	
	public void onStop()
	{
		ConnectionService.unbind(getContext(), _conn);
		getContext().unregisterReceiver(_environment_changed);
	}
	
	
	@Override
	public void onMeasure(int wspec, int hspec)
	{
		int parentw = MeasureSpec.getSize(wspec);
		int parenth = MeasureSpec.getSize(hspec);
		setMeasuredDimension(parentw, parenth);
	}
	
	private void _draw_crisis(Canvas canvas, float fieldscale, Environment.Crisis c)
	{
		canvas.save();
		
		canvas.rotate(-c.getRotation());
		float size = fieldscale * crisis_size;
		
		int x = Math.max(0, (int)(fieldscale * c.getPos().getX() - 0.5*size));
		int y = Math.max(0, (int)(fieldscale * c.getPos().getY() - 0.5*size));
		
		canvas.drawBitmap(crisis_bitmap, null, 
				new RectF(x, y, x + size, y + size), null);
		canvas.restore();
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.argb(255, 0x4d, 0x1b, 0x00));
		if(_conn.service == null) {
			return;
		}
		if(_conn.service.environment == null) {
			return;
		}
		
		int h = getHeight();
		int w = getWidth();
		
		float fieldscale = Math.min((((float)w) / _conn.service.environment.getFieldSize().getX()),
								    (((float)h) / _conn.service.environment.getFieldSize().getY()));
		
		for(Commands.Environment.Element e: _conn.service.environment.getElementsList()) {
			float size = sunflower_size;
			size *= e.getSize() * fieldscale;
		 
			int x = (int)(e.getPos().getX()*fieldscale - 0.5*size);
			int y = (int)(e.getPos().getY()*fieldscale - 0.5*size);
			canvas.drawBitmap(sunflower_bitmap, null, new RectF(x, y, x+size, y+size), null);
		}

		_draw_crisis(canvas, fieldscale, _conn.service.environment.getCrisis());
	}
	
	private BroadcastReceiver _environment_changed = new BroadcastReceiver()
    {
		@Override
		public void onReceive(Context ctx, Intent intent)
		{
			invalidate();
		}
    };
    
}
