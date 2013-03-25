package nl.flarb.crisis;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class CRISISEnvironmentDisplay extends View {
	private Paint paint;
	
	private void init()
	{
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
	}
	

	public CRISISEnvironmentDisplay(Context context)
	{
		super(context);
		init();
	}	
	
	public CRISISEnvironmentDisplay(Context context, AttributeSet attr)
	{
		super(context, attr);
		init();
	}
	
	public CRISISEnvironmentDisplay(Context context, AttributeSet attr, int defStyle)
	{
		super(context, attr, defStyle);
		init();
	}

	@Override
	public void onDraw(Canvas canvas) {
		int h = getHeight();
		int w = getWidth();
		RectF r = new RectF(10.0f, 10.0f, w-10.0f, h-10.0f);
		canvas.drawRoundRect(r, 30, 30, paint);
	}
}
