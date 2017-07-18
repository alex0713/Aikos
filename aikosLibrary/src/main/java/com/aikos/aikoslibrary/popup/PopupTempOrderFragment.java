package com.aikos.aikoslibrary.popup;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.aikos.aikoslibrary.data.TempObj;
import com.aikos.library.R;

import java.lang.reflect.Field;


public class PopupTempOrderFragment extends Fragment {
	
	public static final String EXTRA_KEY_TEMP_OBJ = "EXTRA_KEY_TEMP_OBJ";
	
	NumberPicker numberPickerTemp = null;
	
	int temp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.dialog_temp_order, container, false);
		
		numberPickerTemp = (NumberPicker) view.findViewById(R.id.numberPickerTemp);		
		
		setNumberPickerTextStyle(numberPickerTemp, getResources().getColor(R.color.color_pink), getResources().getDimension(R.dimen.font_size_1));
		
		numberPickerTemp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}			
		});
		
		numberPickerTemp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		
		numberPickerTemp.setMaxValue(TempObj.TempObjNames.length - 1);
		numberPickerTemp.setMinValue(0);
		numberPickerTemp.setDisplayedValues(TempObj.TempObjNames);
		numberPickerTemp.setValue(temp);
		
		
		return view;
	}
	
	
	public void initValues(int tmp) {
		temp = tmp;
		
		if (numberPickerTemp != null) {
			numberPickerTemp.setMaxValue(TempObj.TempObjNames.length - 1);
			numberPickerTemp.setMinValue(0);
			numberPickerTemp.setDisplayedValues(TempObj.TempObjNames);
			numberPickerTemp.setValue(temp);
		}
	}
	
	public void exportData(Intent data) {
		
		data.putExtra(EXTRA_KEY_TEMP_OBJ, numberPickerTemp.getValue());
	}
	
	public static boolean setNumberPickerTextStyle(NumberPicker numberPicker, int color, float fontsize)
	{
	    final int count = numberPicker.getChildCount();
	    for(int i = 0; i < count; i++){
	        View child = numberPicker.getChildAt(i);
	        if(child instanceof EditText){
	            try{
	                Field selectorWheelPaintField = numberPicker.getClass()
	                    .getDeclaredField("mSelectorWheelPaint");
	                selectorWheelPaintField.setAccessible(true);
	                ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
	                ((Paint)selectorWheelPaintField.get(numberPicker)).setTextSize(fontsize);
	                ((EditText)child).setTextColor(color);
	                ((EditText)child).setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
	                numberPicker.invalidate();
	                return true;
	            }
	            catch(NoSuchFieldException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	            catch(IllegalAccessException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	            catch(IllegalArgumentException e){
	                Log.w("setNumberPickerTextColor", e);
	            }
	        }
	    }
	    return false;
	}

}
