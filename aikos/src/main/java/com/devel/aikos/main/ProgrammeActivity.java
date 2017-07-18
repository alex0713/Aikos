package com.devel.aikos.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.aikos.aikoslibrary.data.AikosProgramme;
import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

import java.util.ArrayList;

public class ProgrammeActivity extends FragmentActivity {
	
	ViewPager mViewPager;
	ProgramPagerAdapter mainPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setTitle(getResources().getString(R.string.string_choice_program));
		
		mViewPager = (ViewPager) findViewById(R.id.programPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mainPagerAdapter != null) {
                    mainPagerAdapter.refreshSubView(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
		
		refreshView();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_menu_prog, menu);
		
		return true;
	}
	
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//
//		MenuItem item= menu.findItem(R.id.action_prog_3);
//	    if (item != null) {
//	    	item.setTitle("Copier programme ...");
//		}
//	    menu.getItem(2).setVisible(false);
//
//	    super.onPrepareOptionsMenu(menu);
//	    return true;
//	}

	public void refreshView() {
		
		mainPagerAdapter = new ProgramPagerAdapter(getSupportFragmentManager(), AikosGlobals.getProgramsArray());
		
		if (mViewPager != null) {
			mViewPager.setAdapter(mainPagerAdapter);
		}        
	}
	
	class ProgramPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<AikosProgramme> mprograms;
        ArrayList<ProgrammeFragment> hf = new ArrayList<ProgrammeFragment>();

        public ProgramPagerAdapter(FragmentManager fm, ArrayList<AikosProgramme> mps) {
        	
        	super(fm);
        	
        	mprograms = mps;
            for (int i = 0; i < mprograms.size(); i++) {
                ProgrammeFragment pf = new ProgrammeFragment();
                pf.setpIndex(i);
                hf.add(pf);
            }
        }

        @Override
        public Fragment getItem(int i) {
        	if (i < hf.size()) {
                return hf.get(i);
            }
            return null;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
        	if (mprograms == null) {
				return 0;
			}
            return mprograms.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	return mprograms.get(position).getName();
        }

        public void refreshSubView(int index) {
            hf.get(index).refreshView();
        }
    }
}
