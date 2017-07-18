package com.aikos.aikoslibrary.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.aikos.library.R;

public class PopupPickerActivity extends FragmentActivity {
	
	public static final String EXTRA_KEY_POPUP_TYPE = "pop type";
	
	public static final int POPUP_TYPE_SELECT_None = 0;
	public static final int POPUP_TYPE_SELECT_TIME = 1;
	public static final int POPUP_TYPE_SELECT_TEMP = 2;
	
	private static int popupType = POPUP_TYPE_SELECT_None;

	ViewPager mViewPager;
	ConfigPagerAdapter configPagerAdapter;
	
	PopupTempOrderFragment popupTempOrderFragment = new PopupTempOrderFragment();
	PopupStartEndTimeFragment popupStartEndTimeFragment = new PopupStartEndTimeFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup);
		
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
//		getActionBar().setTitle(getResources().getString(R.string.title_configuration));
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			
			popupType = b.getInt(EXTRA_KEY_POPUP_TYPE, POPUP_TYPE_SELECT_None);
			
			if (popupType == POPUP_TYPE_SELECT_TEMP) {
				
				getActionBar().setTitle(getResources().getString(R.string.targeted_temperature));
				
				int temp = 0;
	    		if (b != null) {
	    			temp = b.getInt(PopupTempOrderFragment.EXTRA_KEY_TEMP_OBJ, 0);
	    		}
	    		
	    		popupTempOrderFragment.initValues(temp);
			} else if (popupType == POPUP_TYPE_SELECT_TIME) {
				
				getActionBar().setTitle(getResources().getString(R.string.Start_and_end_of_the_period));
				
				int sh = 0, sm = 0, eh = 0, em = 0;
	    		if (b != null) {
	    			sh = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_START_HOUR, 0);
	    			sm = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_START_MIN, 0);
	    			eh = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_END_HOUR, 0);
	    			em = b.getInt(PopupStartEndTimeFragment.EXTRA_KEY_END_MIN, 0);			
	    		}
	    		
	    		popupStartEndTimeFragment.initValues(sh, sm, eh, em);
			}
			
		}
		
		
		
		configPagerAdapter = new ConfigPagerAdapter(getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.popupPager);
        mViewPager.setAdapter(configPagerAdapter);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub		
		this.finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	        	onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onOK(View view) {
		Intent data = new Intent();
		
		if (popupType == POPUP_TYPE_SELECT_TEMP) {
    		
			popupTempOrderFragment.exportData(data);
    		
		} else if (popupType == POPUP_TYPE_SELECT_TIME) {
    		
			popupStartEndTimeFragment.exportData(data);

		}
		
		
		setResult(Activity.RESULT_OK, data);
		
		this.finish();
	}
	
	public void onCancel(View view) {
		this.finish();
	}

	class ConfigPagerAdapter extends FragmentStatePagerAdapter {
		
        public ConfigPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (popupType == POPUP_TYPE_SELECT_TEMP) {
            	        		
        		return popupTempOrderFragment;
        		
			} else if (popupType == POPUP_TYPE_SELECT_TIME) {
        		
				return popupStartEndTimeFragment;

			} else {
				return null;
			}
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
        	if (popupType == POPUP_TYPE_SELECT_TEMP ||
        			popupType == POPUP_TYPE_SELECT_TIME) {
				return 1;
			} else {
				return 0;
			}
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if (popupType == POPUP_TYPE_SELECT_TEMP ||
        			popupType == POPUP_TYPE_SELECT_TIME) {
				return "";
			} else {
				return "";
			}
        }
    }
}
