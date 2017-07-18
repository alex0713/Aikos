package com.aikos.aikoslibrary.popup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.aikos.library.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class PopupStartEndTimeFragment extends Fragment {
	
	private final static int TIME_PICKER_INTERVAL = 10;
	
	public static final String EXTRA_KEY_START_HOUR = "EXTRA_KEY_START_HOUR";
	public static final String EXTRA_KEY_START_MIN = "EXTRA_KEY_START_MIN";
	public static final String EXTRA_KEY_END_HOUR = "EXTRA_KEY_END_HOUR";
	public static final String EXTRA_KEY_END_MIN = "EXTRA_KEY_END_MIN";

	TimePicker timePickerStart = null, timePickerEnd = null;
	
	int sh, sm, eh, em;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.dialog_start_end_time, container, false);
		
		timePickerStart = (TimePicker) view.findViewById(R.id.timePickerStart);
		timePickerEnd = (TimePicker) view.findViewById(R.id.timePickerEnd);
		timePickerStart.setIs24HourView(true);
		timePickerEnd.setIs24HourView(true);
		
		timePickerStart.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		timePickerEnd.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		
		try {
			Class<?> classForid = Class.forName("com.android.internal.R$id");
			Field field = classForid.getField("minute");
			
	        NumberPicker mMinuteSpinner = (NumberPicker) timePickerStart.findViewById(field.getInt(null));
	        mMinuteSpinner.setMinValue(0);
	        mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
	        List<String> displayedValues = new ArrayList<String>();
	        for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
	            displayedValues.add(String.format("%02d", i));
	        }
	        mMinuteSpinner.setDisplayedValues(displayedValues
	                .toArray(new String[0]));
	        
	        PopupTempOrderFragment.setNumberPickerTextStyle(mMinuteSpinner, getResources().getColor(R.color.color_pink), getResources().getDimension(R.dimen.font_size_t1));
	        
	        mMinuteSpinner = (NumberPicker) timePickerEnd.findViewById(field.getInt(null));
	        mMinuteSpinner.setMinValue(0);
	        mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
	        mMinuteSpinner.setDisplayedValues(displayedValues
	                .toArray(new String[0]));
	        
	        PopupTempOrderFragment.setNumberPickerTextStyle(mMinuteSpinner, getResources().getColor(R.color.color_pink), getResources().getDimension(R.dimen.font_size_t1));
	        
	        field = classForid.getField("hour");
	        mMinuteSpinner = (NumberPicker) timePickerStart.findViewById(field.getInt(null));
	        PopupTempOrderFragment.setNumberPickerTextStyle(mMinuteSpinner, getResources().getColor(R.color.color_pink), getResources().getDimension(R.dimen.font_size_t1));
	        mMinuteSpinner = (NumberPicker) timePickerEnd.findViewById(field.getInt(null));
	        PopupTempOrderFragment.setNumberPickerTextStyle(mMinuteSpinner, getResources().getColor(R.color.color_pink), getResources().getDimension(R.dimen.font_size_t1));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		timePickerStart.setCurrentHour(sh);
		timePickerStart.setCurrentMinute(sm / TIME_PICKER_INTERVAL);
		timePickerEnd.setCurrentHour(eh);
		timePickerEnd.setCurrentMinute(em / TIME_PICKER_INTERVAL);
		
		timePickerStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){

			@Override
			public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				int stm = arg1 * 6 + arg2;
				int etm = timePickerEnd.getCurrentHour() * 6 + timePickerEnd.getCurrentMinute();
				if (stm >= etm) {
					stm++;
					timePickerEnd.setCurrentHour(stm / 6);
					timePickerEnd.setCurrentMinute(stm % 6);
				}
			}
			
		});
		
		timePickerEnd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){

			@Override
			public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				int stm = timePickerStart.getCurrentHour() * 6 + timePickerStart.getCurrentMinute();
				int etm = arg1 * 6 + arg2;
				if (stm >= etm) {
					etm--;
					timePickerStart.setCurrentHour(etm / 6);
					timePickerStart.setCurrentMinute(etm % 6);
				}
			}
			
		});
		
		return view;
	}
	
	
	public void initValues(int startHour, int startMin, int endHour, int endMin) {

		sh = startHour;
		sm = startMin;
		eh = endHour;
		em = endMin;

		if (timePickerStart != null) {
			timePickerStart.setCurrentHour(sh);
			timePickerStart.setCurrentMinute(sm / TIME_PICKER_INTERVAL);
		}
		if (timePickerEnd != null) {
			timePickerEnd.setCurrentHour(eh);
			timePickerEnd.setCurrentMinute(em / TIME_PICKER_INTERVAL);
		}
	}
	
	public void exportData(Intent data) {
		data.putExtra(EXTRA_KEY_START_HOUR, timePickerStart.getCurrentHour());
		data.putExtra(EXTRA_KEY_START_MIN, timePickerStart.getCurrentMinute() * TIME_PICKER_INTERVAL);
		data.putExtra(EXTRA_KEY_END_HOUR, timePickerEnd.getCurrentHour());
		data.putExtra(EXTRA_KEY_END_MIN, timePickerEnd.getCurrentMinute() * TIME_PICKER_INTERVAL);
	}

}
