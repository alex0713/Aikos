package com.aikos.aikoslibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aikos.aikoslibrary.data.TempObj;
import com.aikos.aikoslibrary.data.TempObjsInADay;
import com.aikos.library.R;


public class ProgramADayView extends View {
	
	protected TimeGraphClickedListener mListener;	
	
	private TempObjsInADay temps = new TempObjsInADay();
	
	private boolean isShowTemp = false;
	
	Context context = null;
	
	float x = 0, y = 0;
	
	public ProgramADayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		initListener();
	}

	public ProgramADayView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		initListener();
	}
	
	public ProgramADayView(Context context, AttributeSet attr) {
        super(context, attr);
        
        this.context = context;
        
        initListener();
    }

	private void initListener() {
		setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(mListener != null) {
		    		int px = ((int)x * 100000) / (ProgramADayView.this.getWidth() * 100000 / TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY);
		    		mListener.onTimeGraphLongPressed(px);
		    	}
				
				return true;
			}
		});
		
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mListener != null) {
		    		int px = ((int)x * 100000) / (ProgramADayView.this.getWidth() * 100000 / TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY);
		    		mListener.onTimeGraphClicked(px);
		    	}				
			}
		});
	}
	public boolean onTouchEvent(MotionEvent event) {
		x = event.getX();
		y = event.getY();
		
		return super.onTouchEvent(event);
		// int eventaction = event.getAction();
		// return gestureDetector.onTouchEvent(event);
	    
	};
	
	public interface TimeGraphClickedListener {
		void onTimeGraphLongPressed(int knobAt);
		void onTimeGraphClicked(int knobAt);
	}
	
	

	public void setOnTimeGraphClickedListener(TimeGraphClickedListener mListener) {
		this.mListener = mListener;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// Round some corners betch!
		super.onDraw(canvas);
		
		if (context == null) {
			return;
		}
		
        final RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setColor(context.getResources().getColor(R.color.Gainsboro));
//        canvas.drawRect(rectF, paint);
        
        int c10 = 100000;
        int oneW10 = getWidth() * c10 / TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY;
        
        for (int i = 0; i < TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY; i++) {
			TempObj temp = temps.getTemp(i);
			if (temp != null) {
				paint.setColor(temp.getGraphColor());
//				RectF rect1 = new RectF(i * oneW, 0, (i + 1) * oneW, getHeight());
				float dh = isShowTemp ? (temps.isDefault(i) ? 0.95f : 1) : 1;
				RectF rect1 = new RectF((i * oneW10) / c10, getHeight() * (1 - dh), ((i + 1) * oneW10) / c10, getHeight() * dh);
		        canvas.drawRect(rect1, paint);
			}
		}
        
        if (isShowTemp) {
        	paint.setColor(context.getResources().getColor(R.color.color_gray));
        	float tsz = Math.max(Math.min(context.getResources().getDimension(R.dimen.font_size_4), getHeight() / 3.0f),
        			context.getResources().getDimension(R.dimen.font_size_6));
        	paint.setTextSize(tsz);
        	
        	Rect bounds = new Rect();
        	TempObj temp1 = temps.getTemp(0);
        	int num1 = 0;
        	for (int i = 1; i < TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY; i++) {
        		TempObj temp2 = temps.getTemp(i);
        		if (!temp1.isEqual(temp2) || i == TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY - 1) {
					int ww = oneW10 * (i - num1) / c10;
					if (i == TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY - 1) ww += oneW10 / c10;
					String txt = temps.getTemp(num1).getTempString();
					
					paint.getTextBounds(txt, 0, txt.length(), bounds);
					if (bounds.width() < ww) {
						canvas.drawText(txt, oneW10 * num1 / c10 + (ww - bounds.width()) / 2, getHeight() / 2 + tsz / 2, paint);
					}
					temp1 = temps.getTemp(i);
					num1 = i;
				}
			}
		}
	}

	public TempObj getTemp(int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		return temps.getTemp(idx);
	}

	public void setTemp(TempObj temp, int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		temps.setTemp(temp, idx);
		invalidate();
	}
	
	public TempObj getTemp(int idx) {
		int nidx = idx % TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY;
		return temps.getTemp(nidx);
	}

	public TempObjsInADay getTemps() {
		return temps;
	}

	public void setTemps(TempObjsInADay temps) {
		this.temps = temps;
		invalidate();
	}

	public void setTemp(TempObj temp, int idx) {
		int nidx = idx % TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY;
		temps.setTemp(temp, nidx);
		invalidate();
	}

	public boolean isShowTemp() {
		return isShowTemp;
	}

	public void setShowTemp(boolean isShowTemp) {
		this.isShowTemp = isShowTemp;
		invalidate();
	}
	
}
