package com.aikos.aikoslibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.aikos.library.R;

public class IntradayRuler extends View {
	
	Context context = null;
	
	Paint paint = new Paint();
	Rect bounds = new Rect();
	
	public IntradayRuler(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public IntradayRuler(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public IntradayRuler(Context context, AttributeSet attr) {
        super(context, attr);
        
        this.context = context;
    }

	@Override
	protected void onDraw(Canvas canvas) {
		// Round some corners betch!
		super.onDraw(canvas);
		
		if (context == null) {
			return;
		}
		
//        final RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        
		paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(context.getResources().getColor(R.color.color_gray));
        
        int c10 = 100000;
        int oneW10 = getWidth() * c10 / 24;
        
        for (int i = 0; i <= 24; i++) {
			canvas.drawLine((i * oneW10) / c10, getHeight() * 2 / 3, (i * oneW10) / c10, getHeight(), paint);
		}
        
        paint.setStrokeWidth(2);
        for (int i = 0; i < 24; i++) {
			canvas.drawLine((i * oneW10) / c10 + oneW10 / c10 / 2, getHeight() * 5 / 6, (i * oneW10) / c10 + oneW10 / c10 / 2, getHeight(), paint);
		}
        
        paint.setTextSize(context.getResources().getDimension(R.dimen.font_size_6));
        paint.setStyle(Paint.Style.FILL);
        
        for (int i = 0; i < 9; i++) {
        	String txt = i * 3 + "h";
			paint.getTextBounds(txt, 0, txt.length(), bounds);
			float xx = i == 0 ? 0 : (i * 3 * oneW10) / c10 - bounds.width() / 2;
			canvas.drawText(txt, xx, getHeight() * 18 / 30, paint);
		}
        
//        canvas.drawRect(rectF, paint);
        

	}

}
