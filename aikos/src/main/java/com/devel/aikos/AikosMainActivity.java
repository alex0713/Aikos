package com.devel.aikos;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.devel.aikos.data.AikosGlobals;
import com.devel.aikos.main.MainConsumeFragment;
import com.devel.aikos.main.MainHeatingFragment;
import com.devel.aikos.main.MainHotwaterFragment;

import java.lang.reflect.Field;

public class AikosMainActivity extends FragmentActivity {
	
	MainConsumeFragment mainConsumeFragment = new MainConsumeFragment();
	MainHeatingFragment mainHeatingFragment = new MainHeatingFragment();
	MainHotwaterFragment mainHotwaterFragment = new MainHotwaterFragment();
	
	public static AikosMainActivity instance = null;
	
	static final int ACTIVITY_LOGIN = 10000;
	
	public static final int ACTIVITY_RESULT_LOGIN_OK = 1000;
	
	public static final int MENU_STATUS_NORMAL = 0;
	public static final int MENU_STATUS_LOADING = 1;
	public static final int MENU_STATUS_HEATING = 2;
	
	private int menuStatus = MENU_STATUS_HEATING;
	
	ViewPager mViewPager;
	MainPagerAdapter mainPagerAdapter;
    int mCurrMainPagerNum = 0;
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private Boolean isEuro = true;
	ListView listviewConsume;
	Spinner spinnerConsumeUsages;
	Spinner spinnerConsumePeriod;
	TextView textviewConsumeEuros;

    MenuItem switchMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aikos_main);
		
		AikosGlobals.initData(this);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        refreshMenu();

        getActionBar().setIcon(R.drawable.ic_aikos);
		
		instance = this;
		
		listviewConsume = (ListView) findViewById(R.id.listviewConsume);
		spinnerConsumeUsages = (Spinner) findViewById(R.id.spinnerConsumeUsages);
		spinnerConsumePeriod = (Spinner) findViewById(R.id.spinnerConsumePeriod);
		textviewConsumeEuros = (TextView) findViewById(R.id.textviewConsumeEuros);
		textviewConsumeEuros.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setIsEuro(!isEuro);
				
			}
		});
		
		setIsEuro(true);
		
		initSpinners();
		mainConsumeFragment.setConsumeCase(0);
        resetListView();
		
        try {
    		ViewConfiguration config = ViewConfiguration.get(this);
    		Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

    		if (menuKeyField != null) {
    			menuKeyField.setAccessible(true);
    			menuKeyField.setBoolean(config, false);
    		}
      	}
      	catch (Exception e) {
      	  // presumably, not relevant
      	}
        
		loginCheck();
	}
	
	private void setIsEuro(boolean isu) {
		isEuro = isu;
		textviewConsumeEuros.setText(getResources().getString(isEuro ? R.string.text_YES : R.string.text_NO));
	}

    private void refreshMenu() {
        if (mCurrMainPagerNum == 0) {
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);

            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayShowTitleEnabled(true);
            getActionBar().setDisplayUseLogoEnabled(false);
            if (switchMenuItem != null) {
                switchMenuItem.setVisible(false);
            }
        } else {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getActionBar().setDisplayHomeAsUpEnabled(false);
            getActionBar().setHomeButtonEnabled(false);

            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setDisplayShowTitleEnabled(true);
            getActionBar().setDisplayUseLogoEnabled(true);
            if (switchMenuItem != null) {
                switchMenuItem.setVisible(true);
            }
        }
    }
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


	public void refreshView() {
		mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(mainPagerAdapter);
        
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
                mCurrMainPagerNum = arg0;
                refreshMenu();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		switch (menuStatus) {
		case MENU_STATUS_NORMAL:
			getMenuInflater().inflate(R.menu.action_menu_normal, menu);
			break;
		case MENU_STATUS_LOADING:
			getMenuInflater().inflate(R.menu.action_menu_loading, menu);
			break;
		case MENU_STATUS_HEATING:
		{
			getMenuInflater().inflate(R.menu.action_menu_heating, menu);
			Switch sw = (Switch) menu.findItem(R.id.switch_menuItem).getActionView();
			sw.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		            onMenuSwitchClicked((Switch) v);
		        }
		    });
		}
			break;
			
		default:
			break;
		}
		
		return true;
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        switchMenuItem = menu.findItem(R.id.switch_menuItem);
        //depending on your conditions, either enable/disable
        if (switchMenuItem != null && mCurrMainPagerNum == 0) {
            switchMenuItem.setVisible(false);
        }

        super.onPrepareOptionsMenu(menu);
        return true;
    }
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int mid = item.getItemId();
		
		switch (mid) {
		case R.id.action_normal_weekend:
			onWeekend();
			break;
		case R.id.action_normal_holidays:
			onHolidays();
			break;
		case R.id.action_normal_configuration:
			goConfigurationPage();
			break;
		case R.id.action_normal_disconnection:
			onDisconnection();
			break;
		default:
			break;
		}
		
		return true;
//		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == ACTIVITY_LOGIN) {
			if (resultCode == ACTIVITY_RESULT_LOGIN_OK) {
				AikosGlobals.isLoggedin = true;
				refreshView();
			} else {
				finish();
			}
		}
		
	}

	public void setOptionMenu(int mtype) {
		menuStatus = mtype;
		invalidateOptionsMenu();
	}

	void loginCheck() {
		if (!AikosGlobals.isLoggedin) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, ACTIVITY_LOGIN);
		} else {
			refreshView();
		}
	}
	
	private void goConfigurationPage() {
		Intent intent = new Intent(this, ConfigurationActivity.class);
		startActivity(intent);
	}
	
	void onDisconnection() {
		AikosGlobals.isLoggedin = false;
		loginCheck();
	}
	
	void onHolidays() {
		
	}

	void onWeekend() {
	
	}
	
	void onMenuSwitchClicked(Switch sw) {
		
	}
	
	private void initSpinners() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.consume_case, R.layout.spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerConsumeUsages.setAdapter(adapter);
		spinnerConsumeUsages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mainConsumeFragment.setConsumeCase(arg2);
				resetListView();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		
		ArrayAdapter<CharSequence> adapterperiod = ArrayAdapter.createFromResource(this, R.array.periods, R.layout.spinner_item);
		adapterperiod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerConsumePeriod.setAdapter(adapterperiod);
		spinnerConsumePeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mainConsumeFragment.setPeriod(arg2);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		
	}
	
	private void resetListView() {
		listviewConsume.setAdapter(new ConsumeListAdapter(
				AikosGlobals.getConsumeItems(mainConsumeFragment.getConsumeCase())));
	}
	
	class ConsumeListAdapter extends BaseAdapter {
		
		String[] mtitles = null;
		
		public ConsumeListAdapter(String[] titles) {
			mtitles = titles;
		}

		@Override
		public int getCount() {
			return mtitles.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			
			View view = arg1;
			
			Context context = AikosMainActivity.this;
			
			if (view == null) {
				LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = li.inflate(R.layout.listitem_main_consume, arg2, false);
			}
			
			TextView textviewConsumeListItem = (TextView) view.findViewById(R.id.textviewConsumeListItem);
			LinearLayout layoutConsumeListItemColor = (LinearLayout) view.findViewById(R.id.layoutConsumeListItemColor);
			CheckBox checkboxConsumeItem = (CheckBox) view.findViewById(R.id.checkboxConsumeItem);
			checkboxConsumeItem.setTag(Integer.valueOf(position));
			checkboxConsumeItem.setChecked(mainConsumeFragment.isChecked(position));
			checkboxConsumeItem.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					int i = (Integer)(((CheckBox)buttonView).getTag());
					mainConsumeFragment.setChecked(i, isChecked);
				}
			});
			
			textviewConsumeListItem.setText(mtitles[position]);
			layoutConsumeListItemColor.setBackgroundColor(AikosGlobals.graphColors[position]);

            view.setTag(checkboxConsumeItem);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkboxConsumeItem = (CheckBox) view.getTag();
                    checkboxConsumeItem.setChecked(!checkboxConsumeItem.isChecked());
                }
            });

			return view;
		}
		
	}
	
	
	
	
	class MainPagerAdapter extends FragmentStatePagerAdapter {
		
        public MainPagerAdapter(FragmentManager fm) {
        	super(fm);            
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
				return mainConsumeFragment;
			} else if (i == 1) {
				return mainHeatingFragment;
			} else {
				return mainHotwaterFragment;
			}
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if (position == 0) {
        		return AikosMainActivity.this.getResources().getString(R.string.text_consumption);
			} else if (position == 1) {
				return AikosMainActivity.this.getResources().getString(R.string.text_heating);
			} else {
				return AikosMainActivity.this.getResources().getString(R.string.text_hotwater);
			}
        }
    }
}
