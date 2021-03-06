package com.devel.aikos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.devel.aikos.config.ConfigAccessManageFragment;
import com.devel.aikos.config.ConfigCalibrerTempFragment;
import com.devel.aikos.config.ConfigContractsFragment;
import com.devel.aikos.config.ConfigProfileFragment;
import com.devel.aikos.config.ConfigRenameFragment;

public class ConfigurationActivity extends FragmentActivity {
	
	ViewPager mViewPager;
	ConfigPagerAdapter configPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setTitle(getResources().getString(R.string.title_configuration));
		
		configPagerAdapter = new ConfigPagerAdapter(getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.configPager);
        mViewPager.setAdapter(configPagerAdapter);
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
	
	static class ConfigPagerAdapter extends FragmentStatePagerAdapter {
		
		final int CONFIG_PAGE_COUNT = 5;
		
		final String[] configTitles = {
			"CALIBRER TEMPERATURE ",
			"RENOMMER  ",
			"CONTRAT ET TARIFS  ",
			"PROFIL ",
			"GESTION DES ACCES",
		};
		
		Fragment[] pageFragments = {
				new ConfigCalibrerTempFragment(),
				new ConfigRenameFragment(),
				new ConfigContractsFragment(),
				new ConfigProfileFragment(),
				new ConfigAccessManageFragment()
			};
		

        public ConfigPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            
            return pageFragments[i];
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return CONFIG_PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return configTitles[position];
        }
    }
}
