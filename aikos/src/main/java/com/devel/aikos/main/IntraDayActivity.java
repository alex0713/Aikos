package com.devel.aikos.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aikos.aikoslibrary.data.TempObj;
import com.aikos.aikoslibrary.data.TempObjsInADay;
import com.aikos.aikoslibrary.data.TempObjsInADay.TimeTempObj;
import com.aikos.aikoslibrary.popup.PopupPickerActivity;
import com.aikos.aikoslibrary.popup.PopupStartEndTimeFragment;
import com.aikos.aikoslibrary.popup.PopupTempOrderFragment;
import com.aikos.aikoslibrary.view.ProgramADayView;
import com.aikos.aikoslibrary.view.ProgramADayView.TimeGraphClickedListener;
import com.aikos.aikoslibrary.view.SliderView;
import com.aikos.aikoslibrary.view.SliderView.KnobValuesChangedListener;
import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

import java.util.ArrayList;


public class IntraDayActivity extends Activity implements KnobValuesChangedListener, TimeGraphClickedListener {
	
	static final int ACTIVITY_POPUP_Time = 1;
	static final int ACTIVITY_POPUP_Temp = 2;

    public static final String EXTRA_KET_TEMPOBJ_DAYS = "tempobj days";
	public static final String EXTRA_KET_TEMPOBJ_ZONE_INDEX = "tempobj zone index";
	public static final String EXTRA_KET_TEMPOBJ_PROG_INDEX = "tempobj program index";

    public static final int EXTRA_KET_TEMPOBJ_ZONE_HOTWATER = -1;

	public int currentTimePickerDlg = -1;
	
	private ArrayList<Integer> dayIndexs = null;
	private int dIndex = 0;
	private int zIndex = 0;
	private int pIndex = 0;
	
	ProgramADayView progADayView;
	SliderView slider;
	
	TempObjsInADay tempObjsBase = new TempObjsInADay();
	TempObjsInADay tempObjsWork = new TempObjsInADay();
	
	LinearLayout linearlayoutIntradayStart;
	LinearLayout linearlayoutIntradayTemp;
	LinearLayout linearlayoutIntradayEnd;
	
	TextView textviewIntradayStart;
	TextView textviewIntradayTemp;
	TextView textviewIntradayEnd;
	
	TextView textviewIntradayCancel;
	TextView textviewIntradaySave;
	
	LinearLayout layoutIntradayGraph;
	LinearLayout layoutIntradayTimeBar;
	
	LinearLayout layoutIntradaySlider;
	
	HorizontalScrollView scrollviewTimeTemp;
	
//	private int currStartHour = 7, currStartMin = 30, currEndHour = 21, currEndMin = 30;
	TimeTempObj currTTO;
	
	MenuItem deleteMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intraday);
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			dayIndexs = b.getIntegerArrayList(EXTRA_KET_TEMPOBJ_DAYS);
			zIndex = b.getInt(EXTRA_KET_TEMPOBJ_ZONE_INDEX, 0);
			pIndex = b.getInt(EXTRA_KET_TEMPOBJ_PROG_INDEX, 0);
		}
		
		String days = "";
		if (dayIndexs != null) {
			for (Integer idx : dayIndexs) {
				days += getResources().getStringArray(R.array.days)[idx] + " ";
			}
			dIndex = dayIndexs.get(0);
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
        if (zIndex == EXTRA_KET_TEMPOBJ_ZONE_HOTWATER) {
            getActionBar().setTitle(R.string.text_hotwater);
        } else {
            getActionBar().setTitle((AikosGlobals.getProgram(pIndex) != null ? AikosGlobals.getProgram(pIndex).getName() : "") + "/"
                    + AikosGlobals.getZones(pIndex).get(zIndex).getName() + "/"
                    + days);
        }
		layoutIntradayGraph = (LinearLayout) findViewById(R.id.layoutIntradayGraph);
		layoutIntradayTimeBar = (LinearLayout) findViewById(R.id.layoutIntradayTimeBar);
		layoutIntradaySlider = (LinearLayout) findViewById(R.id.layoutIntradaySlider);
		
		progADayView = (ProgramADayView) findViewById(R.id.progADayView);
		progADayView.setShowTemp(true);
		progADayView.setOnTimeGraphClickedListener(this);
//		progADayView.setOnLongClickListener(new GraphLongClickListener());
		slider = (SliderView) findViewById(R.id.slider);
		slider.setOnKnobValuesChangedListener(this);
//		slider.setOnLongClickListener(new SliderLongClickListener());


        if (zIndex == EXTRA_KET_TEMPOBJ_ZONE_HOTWATER) {
            tempObjsBase.setTemps(new TempObj(5), -1, -1);
            tempObjsBase.resetDefault();
        } else {
            tempObjsBase.setTemps(AikosGlobals.getZoneTempObjsOfDay(zIndex, dIndex, pIndex));
        }
		currTTO = tempObjsBase.getTimeTempObj(45);
		currTTO.tempObj = tempObjsBase.getDefaultTemp();
		tempObjsWork = new TempObjsInADay(tempObjsBase.getDefaultCode());
		
		scrollviewTimeTemp = (HorizontalScrollView) findViewById(R.id.scrollviewTimeTemp);
		
		linearlayoutIntradayStart = (LinearLayout) findViewById(R.id.linearlayoutIntradayStart);
		linearlayoutIntradayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showStartEndTimeDialog();
			}
		});
		
		linearlayoutIntradayTemp = (LinearLayout) findViewById(R.id.linearlayoutIntradayTemp);
		linearlayoutIntradayTemp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {				
				showTempOrderDialog();				
			}
		});
		
		linearlayoutIntradayEnd = (LinearLayout) findViewById(R.id.linearlayoutIntradayEnd);
		linearlayoutIntradayEnd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showStartEndTimeDialog();
			}
		});
		
		textviewIntradayStart = (TextView) findViewById(R.id.textviewIntradayStart);
		textviewIntradayTemp = (TextView) findViewById(R.id.textviewIntradayTemp);
		textviewIntradayEnd = (TextView) findViewById(R.id.textviewIntradayEnd);
		
		textviewIntradayCancel = (TextView) findViewById(R.id.textviewIntradayCancel);
		textviewIntradayCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCancel();
			}
		});

		textviewIntradaySave = (TextView) findViewById(R.id.textviewIntradaySave);
		textviewIntradaySave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onSave();
			}
		});

	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		refreshGraph();
		refreshLabels();
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == ACTIVITY_POPUP_Temp) {
				
				Bundle b = data.getExtras();
				if (b != null) {
					int temp = b.getInt(PopupTempOrderFragment.EXTRA_KEY_TEMP_OBJ, currTTO.tempObj.getTempCode());
					if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
						currTTO.tempObj = new TempObj(temp);
						resetTimeObj();
					} else {
                        TempObj tempobj = new TempObj(temp);
                        for (int i = 0; i < TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY; i++) {
                            if (tempObjsBase.isDefault(i)) {
                                tempObjsBase.setTemp(tempobj, i);
                            }
                        }
                        tempObjsBase.setDefault(temp);
					}
					
				}
				
				refreshLabels();
				refreshGraph();
				
			} else if (requestCode == ACTIVITY_POPUP_Time) {
				Bundle b = data.getExtras();
				if (b != null) {
					int sh = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_START_HOUR, currTTO.getStartHour());
					int sm = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_START_MIN, currTTO.getStartMin());
					int eh = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_END_HOUR, currTTO.getEndHour());
					int em = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_END_MIN, currTTO.getEndMin());
					currTTO.from = sh * 6 + sm / 10;
					currTTO.to = eh * 6 + em / 10 - 1;
				}

				resetTimeObj();
				refreshLabels();
			}			
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.action_menu_intraday, menu);
		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		deleteMenuItem = menu.findItem(R.id.item_intraday_delete);
	    //depending on your conditions, either enable/disable
	    if (deleteMenuItem != null) {
	    	deleteMenuItem.setVisible(false);
		}
	    
	    super.onPrepareOptionsMenu(menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        case R.id.item_intraday_add:
	        	onAdd();
	        	return true;
	        case R.id.item_intraday_delete:
	        	onDelete();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void showTempOrderDialog() {

        if (zIndex == EXTRA_KET_TEMPOBJ_ZONE_HOTWATER) {
            if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
                currTTO.tempObj.setTempCode(currTTO.tempObj.getTempCode() == 5 ? -1 : 5);
                resetTimeObj();
                refreshLabels();
                refreshGraph();
            }
            return;
        }
		
		Intent i = new Intent(this, PopupPickerActivity.class);
		i.putExtra(PopupPickerActivity.EXTRA_KEY_POPUP_TYPE, PopupPickerActivity.POPUP_TYPE_SELECT_TEMP);
		
		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
			i.putExtra(PopupTempOrderFragment.EXTRA_KEY_TEMP_OBJ, currTTO.tempObj.getTempCode());
		} else {
			i.putExtra(PopupTempOrderFragment.EXTRA_KEY_TEMP_OBJ, tempObjsBase.getDefaultCode());
		}
		
		startActivityForResult(i, ACTIVITY_POPUP_Temp);
		
	}
	
	private void showStartEndTimeDialog() {
		
		if (layoutIntradaySlider.getVisibility() != View.VISIBLE) {
			return;
		}
		
		Intent i = new Intent(this, PopupPickerActivity.class);
		i.putExtra(PopupPickerActivity.EXTRA_KEY_POPUP_TYPE, PopupPickerActivity.POPUP_TYPE_SELECT_TIME);
		i.putExtra(PopupTempOrderFragment.EXTRA_KEY_TEMP_OBJ, currTTO.tempObj.getTempCode());
		i.putExtra(PopupStartEndTimeFragment.EXTRA_KEY_START_HOUR, currTTO.getStartHour());
		i.putExtra(PopupStartEndTimeFragment.EXTRA_KEY_START_MIN, currTTO.getStartMin());
		i.putExtra(PopupStartEndTimeFragment.EXTRA_KEY_END_HOUR, currTTO.getEndHour());
		i.putExtra(PopupStartEndTimeFragment.EXTRA_KEY_END_MIN, currTTO.getEndMin());
		startActivityForResult(i, ACTIVITY_POPUP_Time);
		
	}
	
	private void refreshGraph() {
		
		progADayView.setTemps(tempObjsBase);
		
	}

	private void refreshLabels() {
		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
			textviewIntradayStart.setText(currTTO.getStartTimeString());
			textviewIntradayEnd.setText(currTTO.getEndTimeString());
			textviewIntradayTemp.setText(currTTO.tempObj.getTempString());
		} else {
			textviewIntradayStart.setText("");
			textviewIntradayEnd.setText("");
			textviewIntradayTemp.setText(tempObjsBase.getDefaultCodeString());
		}
	}	
	
	void onAdd() {
		
		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
			applyTimeObjChange();
		}
		
		int scv = scrollviewTimeTemp.getScrollX() + scrollviewTimeTemp.getWidth() * 45 / 100;
		
		int idx = TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY * scv / progADayView.getWidth();
		
		currTTO.from = idx;
		currTTO.to = idx + 5;
		
		currTTO.tempObj = tempObjsBase.getTemp(idx);
		
		resetTimeObj();
	}
	
	void onDelete() {
		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
//			tempObjs.setTemps(new TempObj(), slider.getStartKnobValue(), slider.getEndKnobValue());
			removeTimeObj();
//			refreshGraph();
		}		
	}
	
	void onCancel() {
        if (zIndex == EXTRA_KET_TEMPOBJ_ZONE_HOTWATER) {
            tempObjsBase.setTemps(new TempObj(5), -1, -1);
        } else {
            tempObjsBase.setTemps(AikosGlobals.getZoneTempObjsOfDay(zIndex, dIndex, pIndex).getAllTemps());
        }
		removeTimeObj();
		refreshGraph();
	}
	
	void onSave() {
		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
			applyTimeObjChange();
		}
        if (zIndex == EXTRA_KET_TEMPOBJ_ZONE_HOTWATER) {
            //TODO ...
        } else {
            for (Integer idx : dayIndexs) {
                AikosGlobals.setZoneTempObjsOfDay(tempObjsBase, zIndex, idx, pIndex);
            }
        }
		
		removeTimeObj();
		refreshGraph();

        finish();
	}
	
	private void applyTimeObjChange() {
		tempObjsBase.setTemps(currTTO.tempObj, currTTO.from, currTTO.to);
		refreshGraph();
	}
	
	private void removeTimeObj() {
		if (deleteMenuItem != null) {
	    	deleteMenuItem.setVisible(false);
		}
		layoutIntradaySlider.setVisibility(View.GONE);
		refreshLabels();
	}
	
	private void resetTimeObj() {
		
		layoutIntradaySlider.setVisibility(View.VISIBLE);
		
		tempObjsWork = new TempObjsInADay(tempObjsBase.getDefaultCode());		
		tempObjsWork.setTemps(currTTO.tempObj, currTTO.from, currTTO.to);
		
		slider.setTempObj(currTTO.tempObj);
		slider.setStartKnobValue(currTTO.from);
		slider.setEndKnobValue(currTTO.to + 1);
		
		if (deleteMenuItem != null) {
	    	deleteMenuItem.setVisible(true);
		}
		
		refreshLabels();
	}
	
	
	


	@Override
	public void onValuesChanged(boolean knobStartChanged, boolean knobEndChanged, int knobStart, int knobEnd) {
		
		if (knobStartChanged) {
			if (currTTO.from < knobStart) {
				for (int i = currTTO.from; i < knobStart; i++) {
					tempObjsWork.removeTempAt(i);					
				}
			} else {
				for (int i = knobStart; i < currTTO.from; i++) {
					tempObjsWork.setTemp(currTTO.tempObj, i);
				}
			}
			currTTO.from = knobStart;
		}
		
		if (knobEndChanged) {
			int rEnd = knobEnd - 1;
			if (currTTO.to < rEnd) {
				for (int i = currTTO.to + 1; i <= rEnd; i++) {
					tempObjsWork.setTemp(currTTO.tempObj, i);
				}
			} else {
				for (int i = rEnd + 1; i <= currTTO.to; i++) {
					tempObjsWork.removeTempAt(i);
				}
			}
			currTTO.to = rEnd;
		}
				
		refreshLabels();		
	}

	@Override
	public void onTimeGraphLongPressed(int knobAt) {

		if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
			applyTimeObjChange();
			removeTimeObj();
			return;
		}
		
	}



	@Override
	public void onTimeGraphClicked(int knobAt) {

		if (!tempObjsBase.isDefault(knobAt)) {
			if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
                if (knobAt > currTTO.from && knobAt < currTTO.to) {
                    showTempOrderDialog();
                    return;
                } else {
                    applyTimeObjChange();
                }
			}
			currTTO = tempObjsBase.getTimeTempObj(knobAt);
			tempObjsBase.removeAreaTempsAt(knobAt);
			refreshGraph();
			resetTimeObj();
		} else {
            if (layoutIntradaySlider.getVisibility() == View.VISIBLE) {
                applyTimeObjChange();
                removeTimeObj();
            } else {
                showTempOrderDialog();
            }
        }
	}



	@Override
	public void onMoveValuePoint(int pX, int pY, int knobStartPX, int knobEndPX, boolean isMoving) {

		int dd = (int) getResources().getDimension(R.dimen.padding_small);
		if (isMoving) {
			int dX = 10 + dd;
			if (scrollviewTimeTemp.getScrollX() + scrollviewTimeTemp.getWidth() < knobEndPX + dX &&
					scrollviewTimeTemp.getScrollX() < knobStartPX - dX) {
				
				scrollviewTimeTemp.scrollTo(knobEndPX + dX - scrollviewTimeTemp.getWidth(), scrollviewTimeTemp.getScrollY());
				
			} else if (scrollviewTimeTemp.getScrollX() + scrollviewTimeTemp.getWidth() > knobEndPX + dX &&
					scrollviewTimeTemp.getScrollX() > knobStartPX - dX) {
				
				scrollviewTimeTemp.scrollTo(knobStartPX - dX, scrollviewTimeTemp.getScrollY());
				
			}
		} else {
			int dX = 40;
			if (scrollviewTimeTemp.getScrollX() + scrollviewTimeTemp.getWidth() < pX + dX) {
				scrollviewTimeTemp.scrollTo(pX + dX - scrollviewTimeTemp.getWidth(), scrollviewTimeTemp.getScrollY());
			} else if (scrollviewTimeTemp.getScrollX() > pX - dX) {
				scrollviewTimeTemp.scrollTo(pX - dX, scrollviewTimeTemp.getScrollY());
			}
		}
		
	}

    @Override
    public void onSliderClicked() {
        showTempOrderDialog();
    }


}
