package com.devel.aikos.main;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aikos.aikoslibrary.data.AikosZone;
import com.aikos.aikoslibrary.view.ProgramADayView;
import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

import java.util.ArrayList;

public class ProgramEditFragment extends Fragment {
	
	private int pIndex = -1;
	private int zIndex = -1;
	
	Context context = null;
	
	LinearLayout[] linearlayoutDay = null;
	LinearLayout[] linearlayoutDayBar = null;
	TextView[] textviewProgEditDay = null;
	ProgramADayView[] progADayView = null;
	ImageView[] imageViewSplit = null;
	ImageView[] imageViewDrag = null;

    LinearLayout[] linearlayoutSplitbar = null;
	
	String[] dayNames;
	
	AikosZone zone = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main_program_edit, container, false);
		
		context = getActivity();
		dayNames = getResources().getStringArray(R.array.days);

        zone = AikosGlobals.getZones(pIndex).get(zIndex);
		
		linearlayoutDay = new LinearLayout[] {
			(LinearLayout) view.findViewById(R.id.linearlayoutDay1),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay2),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay3),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay4),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay5),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay6),
			(LinearLayout) view.findViewById(R.id.linearlayoutDay7)
		};
		int i = 0;
		for (LinearLayout v : linearlayoutDay) {
			v.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}
		
		linearlayoutDayBar = new LinearLayout[] {
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar1),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar2),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar3),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar4),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar5),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar6),
			(LinearLayout) view.findViewById(R.id.linearlayoutDayBar7)
		};
		i = 0;
		for (LinearLayout iv : linearlayoutDayBar) {
			iv.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}
		
		textviewProgEditDay = new TextView[] {
			(TextView) view.findViewById(R.id.textviewProgEditDay1),
			(TextView) view.findViewById(R.id.textviewProgEditDay2),
			(TextView) view.findViewById(R.id.textviewProgEditDay3),
			(TextView) view.findViewById(R.id.textviewProgEditDay4),
			(TextView) view.findViewById(R.id.textviewProgEditDay5),
			(TextView) view.findViewById(R.id.textviewProgEditDay6),
			(TextView) view.findViewById(R.id.textviewProgEditDay7)
		};
		i = 0;
		for (TextView iv : textviewProgEditDay) {
			iv.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}
		
		progADayView = new ProgramADayView[] {
			(ProgramADayView) view.findViewById(R.id.progADayView1),
			(ProgramADayView) view.findViewById(R.id.progADayView2),
			(ProgramADayView) view.findViewById(R.id.progADayView3),
			(ProgramADayView) view.findViewById(R.id.progADayView4),
			(ProgramADayView) view.findViewById(R.id.progADayView5),
			(ProgramADayView) view.findViewById(R.id.progADayView6),
			(ProgramADayView) view.findViewById(R.id.progADayView7)
		};
		i = 0;
		for (ProgramADayView iv : progADayView) {
			iv.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}

        linearlayoutSplitbar = new LinearLayout[] {
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar1),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar2),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar3),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar4),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar5),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar6),
                (LinearLayout) view.findViewById(R.id.linearlayoutSplitbar7)
        };
		
		imageViewSplit = new ImageView[] {
			(ImageView) view.findViewById(R.id.imageViewSplit1),
			(ImageView) view.findViewById(R.id.imageViewSplit2),
			(ImageView) view.findViewById(R.id.imageViewSplit3),
			(ImageView) view.findViewById(R.id.imageViewSplit4),
			(ImageView) view.findViewById(R.id.imageViewSplit5),
			(ImageView) view.findViewById(R.id.imageViewSplit6),
			(ImageView) view.findViewById(R.id.imageViewSplit7)
		};
		i = 0;
		for (ImageView iv : imageViewSplit) {
			iv.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}
		
		imageViewDrag = new ImageView[] {
			(ImageView) view.findViewById(R.id.imageViewDrag1),
			(ImageView) view.findViewById(R.id.imageViewDrag2),
			(ImageView) view.findViewById(R.id.imageViewDrag3),
			(ImageView) view.findViewById(R.id.imageViewDrag4),
			(ImageView) view.findViewById(R.id.imageViewDrag5),
			(ImageView) view.findViewById(R.id.imageViewDrag6),
			(ImageView) view.findViewById(R.id.imageViewDrag7)
		};
		i = 0;
		for (ImageView iv : imageViewDrag) {
			iv.setTag(Integer.valueOf(zone.getZoneNumber() * 100 + i++));
		}


		refreshView();
        
        return view;
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		
		refreshView();
	}

    public void refreshView() {
		if (context == null) {
			return;
		}
		
		for (int i = 0; i < 7; i++) {
			linearlayoutDay[i].setVisibility(View.INVISIBLE);
			linearlayoutDay[i].setBackgroundColor(getResources().getColor(R.color.color_white));
			imageViewDrag[i].setOnLongClickListener(null);
			linearlayoutDay[i].setOnDragListener(null);
			imageViewSplit[i].setOnClickListener(null);
			linearlayoutDayBar[i].setOnClickListener(null);
		}
		
		for (int i = 0; i < zone.getDDCount(); i++) {
			ArrayList<Integer> dd = zone.getDDDays(i);
			if (dd.size() > 0) {
				if (dd.size() == 1) {
					imageViewSplit[i].setVisibility(View.INVISIBLE);
                    linearlayoutSplitbar[i].setVisibility(View.INVISIBLE);
					textviewProgEditDay[i].setText(dayNames[dd.get(0)]);
				} else {
					imageViewSplit[i].setVisibility(View.VISIBLE);
                    linearlayoutSplitbar[i].setVisibility(View.VISIBLE);
					imageViewSplit[i].setOnClickListener(new OnSplitListener());
					String lbl = dayNames[dd.get(0)];
					for (int j = 1; j < dd.size(); j++) {
						lbl += " | " + dayNames[dd.get(j)];
					}
					textviewProgEditDay[i].setText(lbl);
				}
				
				progADayView[i].setTemps(AikosGlobals.getZoneTempObjsOfDay(zIndex, dd.get(0), pIndex));
				
				linearlayoutDay[i].setVisibility(View.VISIBLE);
				imageViewDrag[i].setOnLongClickListener(new PELongClickListener());
				linearlayoutDay[i].setOnDragListener(new PEDragListener());
				linearlayoutDayBar[i].setOnClickListener(new OnClickProgramListener());				
				progADayView[i].setOnClickListener(new OnClickProgramListener());
				
			}
		}
	}
	
	

	void mergeDays(final int srcIndex, final int dstIndex) {
		
		zone.mergeTwoDD(srcIndex, dstIndex);
		
		refreshView();

	}
	
	void splitDays(int dindex) {
		
		zone.splitDD(dindex);
		
		refreshView();
	}
	
	
	
	void goIntraDayPage(int index) {
		Intent i = new Intent(getActivity(), IntraDayActivity.class);
		i.putExtra(IntraDayActivity.EXTRA_KET_TEMPOBJ_DAYS, zone.getDDDays(index));
		i.putExtra(IntraDayActivity.EXTRA_KET_TEMPOBJ_ZONE_INDEX, zIndex);
		i.putExtra(IntraDayActivity.EXTRA_KET_TEMPOBJ_PROG_INDEX, pIndex);
		getActivity().startActivity(i);
	}
	
	
	public int getpIndex() {
		return pIndex;
	}

	public void setpIndex(int pIndex) {
		this.pIndex = pIndex;
	}


	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}


	private final class OnSplitListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			
			int tag = (Integer)arg0.getTag() % 100;
			splitDays(tag);
		}
		
	};
	
	private final class OnClickProgramListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			
			int tag = (Integer)arg0.getTag() % 100;
			goIntraDayPage(tag);
		}
		
	};
	
	private final class PELongClickListener implements OnLongClickListener {

	    // called when the item is long-clicked
		@Override
		public boolean onLongClick(View view) {
			
			int idx = ((Integer)view.getTag() % 100) % linearlayoutDay.length;
			// create it from the object's tag
			ClipData.Item item = new ClipData.Item("item " + idx);

	        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
	        ClipData data = new ClipData("day", mimeTypes, item);
	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(linearlayoutDay[idx]) {

				@Override
				public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
					super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
					shadowTouchPoint.set(shadowSize.x * 95 / 100, shadowSize.y / 2);
				}

				@Override
				public void onDrawShadow(Canvas canvas) {
//					canvas.scale(0.95f, 0.95f);
					super.onDrawShadow(canvas);
				}
	        	
	        };
	   
	        view.startDrag( data, //data to be dragged
	        				shadowBuilder, //drag shadow
	        				linearlayoutDay[idx], //local data about the drag and drop operation
	        				0   //no needed flags
	        			  );
	        
	        isDroped = false;
            isProcessed = false;
	        linearlayoutDay[idx].setVisibility(View.INVISIBLE);
	        return true;
		}	
	}

    private static boolean isDroped = false;
    private static boolean isProcessed = false;
	private class PEDragListener implements OnDragListener {

		int normalColor = getResources().getColor(R.color.color_white);
		int targetColor = getResources().getColor(R.color.color_light_gray);

		@Override
		public boolean onDrag(View v, DragEvent event) {
	  
			View view = (View) event.getLocalState();
			int dstTag = (Integer)v.getTag();
			int srcTag = -1;
			if (view != null) {
	        	srcTag = (Integer)view.getTag();
	        }
			// Handles each of the expected events
		    switch (event.getAction()) {
		    
		    //signal for the start of a drag and drop operation.
		    case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;

            case DragEvent.ACTION_DRAG_LOCATION:

                break;
		        
		    //the drag point has entered the bounding box of the View
		    case DragEvent.ACTION_DRAG_ENTERED:
		    	if (dstTag != srcTag) {
		    		v.setBackgroundColor(targetColor);
				}
		        break;
		        
		    //the user has moved the drag shadow outside the bounding box of the View
		    case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackgroundColor(normalColor);	//change the shape of the view back to normal
		        break;
		        
		    //drag shadow has been released,the drag point is within the bounding box of the View
		    case DragEvent.ACTION_DROP:
                if (srcTag != dstTag) {
                    mergeDays(srcTag % 100, dstTag % 100);
                    isDroped = true;
                }
		    	break;
		    	  
		    //the drag and drop operation has concluded.
		    case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackgroundColor(normalColor);	//go back to normal shape
                if (!isDroped && !isProcessed) {
		        	view.setVisibility(View.VISIBLE);
                    isProcessed = true;
				}
		    break;
		    default:
		        break;
		    }
		    return true;
		}
	}
}
