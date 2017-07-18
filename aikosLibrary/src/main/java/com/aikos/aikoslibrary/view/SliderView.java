package com.aikos.aikoslibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.aikos.aikoslibrary.data.TempObj;
import com.aikos.library.R;



public class SliderView extends View {
	protected KnobValuesChangedListener knobValuesChangedListener;	
	private Knob[] knobs = new Knob[2]; // array that holds the knobs
	private int balID = 0; // variable to know what knob is being dragged  
	private Point pointKnobStart, pointKnobEnd;
	private boolean initialisedSlider;
	
	private int valueMax = 144;
	private int valueMin = 0;
	
	private int startKnobValue = valueMax / 4;
	private int endKnobValue = valueMax / 2;//value to know the knob position e.g: 0,40,..,100
	
	private int startKnobValueTmp, endKnobValueTmp;//the position on the X axis
	private double sliderWidth, sliderHeight;//real size of the view that holds the slider
	private Paint paintSelected, paintNotSelected, paintMark;
	private Rect rectangleSelected;
	RectF rectangleMark;
	RectF rectangleThumb1;
	RectF rectangleThumb2;
	private Rect rectangleThumbS1, rectangleThumbS2;
	private int startX, endX, startY, endY;//variables for the rectangles
	
	private TempObj tempObj = new TempObj();
	Rect tmpBounds = new Rect();
	
	double ratio = 1;
	
	private int left_bound;
	private int right_bound;
	private int[] thumbWidth = {(int) getResources().getDimension(R.dimen.slider_thumb_width),
			(int) getResources().getDimension(R.dimen.slider_thumb_width)};

    GestureDetector mDetector = null;
	
	public SliderView(Context context) {
		super(context);
		setFocusable(true);

        initDetector();
	}
	   
	public SliderView(Context context, AttributeSet attrs) {
		super(context, attrs);	   
		setFocusable(true);    
		pointKnobStart = new Point();
		pointKnobEnd = new Point();
		initialisedSlider = false;

        initDetector();

	}

    void initDetector() {
        mDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(final MotionEvent e) {
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e) {
                if(knobValuesChangedListener != null) {
                    int X = (int)e.getX();
                    int Y = (int)e.getY();
                    if (X > knobs[0].getX() && X < knobs[1].getX()) {
                        knobValuesChangedListener.onSliderClicked();
                        return true;
                    }
                }
                return super.onSingleTapConfirmed(e);
            }
        });
    }

	@Override 
	protected void onDraw(Canvas canvas) {
		//background for slider
//		canvas.drawColor(Color.CYAN);
		//initialise data for knobs , slider
		
		if(!initialisedSlider) {
			initialisedSlider = true;
			sliderWidth = getMeasuredWidth();
			sliderHeight = getMeasuredHeight();
			
			left_bound = 0;
        	right_bound = (int)sliderWidth;
			
			pointKnobStart.x = (int)((sliderWidth)/6);
			pointKnobStart.y = (int)(sliderHeight/2.0);
			pointKnobEnd.x = (int)(7*(sliderWidth)/12);
			pointKnobEnd.y = (int)(sliderHeight/2.0);
		    
			knobs[0] = new Knob(getContext(),R.drawable.slider_thumb_lt, pointKnobStart);
			knobs[1] = new Knob(getContext(),R.drawable.slider_thumb_rt, pointKnobEnd);
			knobs[0].setID(1);
			knobs[1].setID(2);
			
			knobValuesChanged(true, true, getStartKnobValue(), getEndKnobValue());
			
			paintSelected = new Paint();//the paint between knobs
			paintSelected.setColor(tempObj.getGraphColor());
			paintNotSelected = new Paint();//the paint outside knobs
			paintNotSelected.setColor(0xFFeea837);
			paintMark = new Paint();//the paint for the slider data(the values) 
			paintMark.setColor(Color.WHITE);
			
			//rectangles that define the line between and outside of knob
			rectangleSelected = new Rect();
			rectangleMark = new RectF(); 
			
//			thumbWidth[0] = knobs[0].getBitmap().getWidth();
//			thumbWidth[1] = knobs[1].getBitmap().getWidth();
			
			rectangleThumb1 = new RectF(0, 0, thumbWidth[0], (int) sliderHeight);
			rectangleThumb2 = new RectF(0, 0, thumbWidth[1], (int) sliderHeight);
			rectangleThumbS1 = new Rect(0, 0, knobs[0].getBitmap().getWidth(), knobs[0].getBitmap().getHeight());
			rectangleThumbS2 = new Rect(0, 0, knobs[1].getBitmap().getWidth(), knobs[1].getBitmap().getHeight());
			
			int delta_bound = right_bound - left_bound;
        	int delta_val = valueMax-valueMin;
        	ratio = (double)delta_bound/delta_val;
        	if (ratio == 0) ratio = 1;
        	
        	knobs[0].setX((int)((this.startKnobValue + valueMin) * ratio + left_bound - thumbWidth[0] / 2));
        	knobs[1].setX((int)((this.endKnobValue + valueMin) * ratio + left_bound - thumbWidth[1] / 2));
        	
		}
		
		//rectangle between knobs
		paintSelected.setColor(tempObj.getGraphColor());
		startX = knobs[0].getX()+thumbWidth[0];
		endX = knobs[1].getX();
		startY = 0;
		endY = (int)(sliderHeight);
		rectangleSelected.set(startX, startY, endX, endY);
		if (startX <= endX) {
			canvas.drawRect(rectangleSelected, paintSelected);
		}
		
//		rectangleThumb1.set(knobs[0].getX(), 0, knobs[0].getX() + thumbWidth[0], (int)(sliderHeight));
//		rectangleThumb2.set(knobs[1].getX(), 0, knobs[1].getX() + thumbWidth[1], (int)(sliderHeight));
//		
//		canvas.drawRoundRect(rectangleThumb1, 0, 0, paintSelected);
//		canvas.drawRoundRect(rectangleThumb2, 0, 0, paintSelected);
//		
//		rectangleMark.set(
//				rectangleThumb1.left + (rectangleThumb1.width() / 2) + 1,
//				(float)(sliderHeight / 3),
//				rectangleThumb1.left + (rectangleThumb1.width() / 2) + 5,
//				(float)(sliderHeight * 2 / 3));
//		canvas.drawRect(rectangleMark, paintMark);
//		rectangleMark.set(
//				rectangleThumb1.left + (rectangleThumb1.width() / 2) - 5,
//				(float)(sliderHeight / 3),
//				rectangleThumb1.left + (rectangleThumb1.width() / 2) - 1,
//				(float)(sliderHeight * 2 / 3));
//		canvas.drawRect(rectangleMark, paintMark);
//		rectangleMark.set(
//				rectangleThumb2.left + (rectangleThumb2.width() / 2) + 1,
//				(float)(sliderHeight / 3),
//				rectangleThumb2.left + (rectangleThumb2.width() / 2) + 5,
//				(float)(sliderHeight * 2 / 3));
//		canvas.drawRect(rectangleMark, paintMark);
//		rectangleMark.set(
//				rectangleThumb2.left + (rectangleThumb2.width() / 2) - 5,
//				(float)(sliderHeight / 3),
//				rectangleThumb2.left + (rectangleThumb2.width() / 2) - 1,
//				(float)(sliderHeight * 2 / 3));
//		canvas.drawRect(rectangleMark, paintMark);
		
		rectangleThumb1.set(knobs[0].getX() + thumbWidth[0] / 2, 0, knobs[0].getX() + thumbWidth[0], (int)(sliderHeight));
		rectangleThumb2.set(knobs[1].getX(), 0, knobs[1].getX() + thumbWidth[1] / 2, (int)(sliderHeight));
		
		canvas.drawRoundRect(rectangleThumb1, 0, 0, paintSelected);
		canvas.drawRoundRect(rectangleThumb2, 0, 0, paintSelected);
		
		rectangleMark.set(
				rectangleThumb1.left + 1,
				(float)(sliderHeight / 3),
				rectangleThumb1.left + 5,
				(float)(sliderHeight * 2 / 3));
		canvas.drawRect(rectangleMark, paintMark);
		rectangleMark.set(
				rectangleThumb1.left - 5,
				(float)(sliderHeight / 3),
				rectangleThumb1.left - 1,
				(float)(sliderHeight * 2 / 3));
		canvas.drawRect(rectangleMark, paintMark);
		rectangleMark.set(
				rectangleThumb2.right + 1,
				(float)(sliderHeight / 3),
				rectangleThumb2.right + 5,
				(float)(sliderHeight * 2 / 3));
		canvas.drawRect(rectangleMark, paintMark);
		rectangleMark.set(
				rectangleThumb2.right - 5,
				(float)(sliderHeight / 3),
				rectangleThumb2.right - 1,
				(float)(sliderHeight * 2 / 3));
		canvas.drawRect(rectangleMark, paintMark);
		
		
//		paintMark.setColor(getResources().getColor(R.color.Gray));
    	float tsz = Math.max(Math.min(getResources().getDimension(R.dimen.font_size_4), getHeight() / 3.0f),
    			getResources().getDimension(R.dimen.font_size_6));
    	paintMark.setTextSize(tsz);
    	
    	String txt = tempObj.getTempString();
    	paintMark.getTextBounds(txt, 0, txt.length(), tmpBounds);    	
    	canvas.drawText(txt, rectangleSelected.left + (rectangleSelected.width() - tmpBounds.width()) / 2, getHeight() / 2 + tsz / 2, paintMark);
		
//		canvas.drawBitmap(knobs[0].getBitmap(), rectangleThumbS1, rectangleThumb1, null);
//		canvas.drawBitmap(knobs[1].getBitmap(), rectangleThumbS2, rectangleThumb2, null);
	}
	    
	int bX = 0;
	public boolean onTouchEvent(MotionEvent event) {

        if (mDetector != null && mDetector.onTouchEvent(event)) {
            return true;
        }

		int eventaction = event.getAction();     
		int X = (int)event.getX(); 
		int Y = (int)event.getY();
		
		switch (eventaction) { 
			//Touch down to check if the finger is on a knob
			case MotionEvent.ACTION_DOWN:
				balID = 0;
				int i = 0;
				for (Knob knob : knobs) {				
					int delta = 5;
					int pX = knob.getX();
					if (X > (pX - delta) && X < pX + thumbWidth[i] + delta) {
						balID = knob.getID();
					}
					i++;
				}
				
				if (balID == 0) {
					if (X > knobs[0].getX() && X < knobs[1].getX()) {
						balID = 100;
						bX = X;
					}
				}
				
				if (balID > 0) {
					getParent().requestDisallowInterceptTouchEvent(true);
				} else {
					return false;
				}
				
				break; 
			
			 //Touch drag with the knob
	        case MotionEvent.ACTION_MOVE:	
	        	startKnobValueTmp = 0;
	        	endKnobValueTmp = 0;
	        	
	        	// knob position from centre
	        	int left_knob = knobs[0].getX() + thumbWidth[0] / 2;
	        	int right_knob = knobs[1].getX() + thumbWidth[1] / 2;
	        	
	        	// The calculated knob value using
	        	// the bounds, ratio, and actual knob position 
	        	startKnobValueTmp = (int)((valueMax*ratio - right_bound + left_knob + ratio/2)/ratio);
	        	endKnobValueTmp = (int)((valueMax*ratio - right_bound + right_knob + ratio/2)/ratio);

	        	//the first knob should be between the left bound and the second knob
	        	if(balID == 1) {
	        		if(X < left_bound) 
	        			X = left_bound;
	        		if(X >= knobs[1].getX() + thumbWidth[1] / 2)
	        			X = knobs[1].getX() + thumbWidth[1] / 2;
	        		
	        		knobs[0].setX(X - thumbWidth[0] / 2);
	        		
	        		//if the start value has changed then we pass it to the listener
	        		if(startKnobValueTmp != getStartKnobValue()) {
	        			this.startKnobValue = Math.min(startKnobValueTmp, this.endKnobValue);
		        		knobValuesChanged(true, false, getStartKnobValue(), getEndKnobValue());	            	
		        	}	            
	        	}
	        	//the second knob should between the first knob and the right bound
	        	if(balID == 2) {
	        		if(X > right_bound) 
	        			X = right_bound;
	        		if(X <= knobs[0].getX() + thumbWidth[0] / 2)
	        			X = knobs[0].getX() + thumbWidth[0] / 2;
	        		
	        		knobs[1].setX(X - thumbWidth[1] / 2);
	        		
	        		//if the end value has changed then we pass it to the listener
		        	if(endKnobValueTmp != getEndKnobValue()) {
		        		this.endKnobValue = Math.max(endKnobValueTmp, this.startKnobValue);
		        		knobValuesChanged(false, true, getStartKnobValue(), getEndKnobValue());	            	
		        	}
	        	}
	        	
	        	if (balID == 100) {
	        		int dx = X - bX;
	        		
	        		if (knobs[0].getX() + thumbWidth[0] / 2 + dx < left_bound) {
	        			dx = 0;
					}
	        		
	        		if (knobs[1].getX() + thumbWidth[0] / 2 + dx > right_bound) {
	        			dx = 0;
					}
	        		
	        		knobs[0].setX(knobs[0].getX() + dx);
	        		knobs[1].setX(knobs[1].getX() + dx);
	        		
	        		if(startKnobValueTmp != getStartKnobValue() || endKnobValueTmp != getEndKnobValue()) {
	        			this.startKnobValue = Math.min(startKnobValueTmp, this.endKnobValue);
	        			this.endKnobValue = Math.max(endKnobValueTmp, this.startKnobValue);
	        			knobValuesChanged(true, true, getStartKnobValue(), getEndKnobValue());	            	
		        	}
	        		
					bX = X;
				}
	        	
	        	if (balID > 0) {
					knobMoveValuePoint(X, Y,
							knobs[0].getX() + thumbWidth[0] / 2, //(int)((getStartKnobValue() + valueMin) * ratio + left_bound),
							knobs[1].getX() + thumbWidth[1] / 2, //(int)((getEndKnobValue() + valueMin) * ratio + left_bound),
							balID < 100 ? false : true );
				}
	        	
	        	break;
	        	
	        // Touch drop - actions after knob is released are performed   
	        case MotionEvent.ACTION_UP:
	        	knobs[0].setX((int)((getStartKnobValue() + valueMin) * ratio + left_bound - thumbWidth[0] / 2));
	        	knobs[1].setX((int)((getEndKnobValue() + valueMin) * ratio + left_bound - thumbWidth[1] / 2));
	        	balID = 0;
	        	break; 
		}	        
		
		 // Redraw the canvas
		invalidate();  
		return true; 
	}

	public int getStartKnobValue() {
		return startKnobValue;
	}

	public void setStartKnobValue(int startKnobValue) {		
		this.startKnobValue = startKnobValue;//Math.min(startKnobValue, this.endKnobValue);
		if (initialisedSlider) {
			knobs[0].setX((int)((this.startKnobValue + valueMin) * ratio + left_bound - thumbWidth[0] / 2));
		}		
		invalidate();
	}

	public int getEndKnobValue() {
		return endKnobValue;
	}

	public void setEndKnobValue(int endKnobValue) {		
		this.endKnobValue = endKnobValue;//Math.max(endKnobValue, this.startKnobValue);
		if (initialisedSlider) {
			knobs[1].setX((int)((this.endKnobValue + valueMin) * ratio + left_bound - thumbWidth[1] / 2));
		}
		invalidate();
	}
	
	/**
	 * Interface which defines the knob values changed listener method
	 */
	public interface KnobValuesChangedListener {
		void onValuesChanged(boolean knobStartChanged, boolean knobEndChanged, int knobStart, int knobEnd);
		void onMoveValuePoint(int pX, int pY, int knobStartPX, int knobEndPX, boolean isMoving);
        void onSliderClicked();
	}
	
	/**
	 * Method applied to the instance of SliderView
	 */
	public void setOnKnobValuesChangedListener (KnobValuesChangedListener l) {
		knobValuesChangedListener = l;
	}
	    
	/**
	 * Method used by knob values changed listener
	 */
	private void knobValuesChanged(boolean knobStartChanged, boolean knobEndChanged, int knobStart, int knobEnd) {
		if(knobValuesChangedListener != null)
			knobValuesChangedListener.onValuesChanged(knobStartChanged, knobEndChanged, knobStart, knobEnd);
	}
	
	private void knobMoveValuePoint(int pX, int pY, int knobStartPX, int knobEndPX, boolean isMoving) {
		if(knobValuesChangedListener != null)
			knobValuesChangedListener.onMoveValuePoint(pX, pY, knobStartPX, knobEndPX, isMoving);
	}
	
	
	public TempObj getTempObj() {
		return tempObj;
	}

	public void setTempObj(TempObj tObj) {
		this.tempObj = tObj;
		invalidate();
	}

	private class Knob  {
		private Bitmap img; // the image of the knob
		private int coordX = 0; // the x coordinate at the canvas
		private int coordY = 0; // the y coordinate at the canvas
		private int id; // gives every knob his own id
	 
		public Knob(Context context, int drawable) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			img = BitmapFactory.decodeResource(context.getResources(), drawable); 
		}
			
		public Knob(Context context, int drawable, Point point) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			img = BitmapFactory.decodeResource(context.getResources(), drawable);
			coordX= point.x;
			coordY = point.y;
		}
					
		void setX(int newValue) {
			coordX = newValue;
		}
			
		public int getX() {
			return coordX;
		}

		void setY(int newValue) {
			coordY = newValue;
		}
			
		public int getY() {
			return coordY;
		}
		
		public void setID(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
			
		public Bitmap getBitmap() {
			return img;
		}		
	}
}