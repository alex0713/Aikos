package com.devel.aikos.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aikos.aikoslibrary.data.AikosZone;
import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

import java.util.ArrayList;

public class ProgramEditActivity extends FragmentActivity {

	public static final String EXTRA_KEY_PROGRAM_INDEX = "zone index";
	
	ViewPager mViewPager;
	ProgramEditPagerAdapter mainPagerAdapter;
	
	int pIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_edit);
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			pIndex = b.getInt(EXTRA_KEY_PROGRAM_INDEX);
		} 
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (AikosGlobals.getProgramsArray().size() > pIndex && pIndex >= 0) {
			getActionBar().setTitle(AikosGlobals.getProgram(pIndex).getName());
		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
		
		mViewPager = (ViewPager) findViewById(R.id.programEditPager);
		
		refreshView();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
            case R.id.action_copy_zone:
                onShowCopyZoneDialog();
                return true;
            case R.id.action_merge_zones:
                onShowMergeZonesDialog();
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
//	    //depending on your conditions, either enable/disable
//	    if (item != null) {
//	    	item.setTitle("Copier programme ...");
//		}
//	    menu.getItem(2).setVisible(false);
//
//	    super.onPrepareOptionsMenu(menu);
//	    return true;
//	}

	public void refreshView() {
		
		mainPagerAdapter = new ProgramEditPagerAdapter(getSupportFragmentManager(), AikosGlobals.getZones(pIndex));
		
		if (mViewPager != null) {
			mViewPager.setAdapter(mainPagerAdapter);
		}
	}
	
	class ProgramEditPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<AikosZone> mzones;

        ArrayList<ProgramEditFragment> fragments;

        public ProgramEditPagerAdapter(FragmentManager fm, ArrayList<AikosZone> mzs) {
        	
        	super(fm);
        	
        	mzones = mzs;

            fragments = new ArrayList<ProgramEditFragment>();

            for (int i = 0; i < mzones.size(); i++) {
                ProgramEditFragment hf = new ProgramEditFragment();
                hf.setpIndex(pIndex);
                hf.setzIndex(i);
                fragments.add(hf);
            }
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= fragments.size())
                return null;
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
        	if (mzones == null) {
				return 0;
			}
            return mzones.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if (position >= 0 && position < mzones.size()) {
        		return mzones.get(position).getName().toUpperCase();
			}
			return "";
        }
    }

    int copyedIndex = -1;
    void onShowCopyZoneDialog() {
        final int zindex = mViewPager.getCurrentItem();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_copy_zone);
        dialog.setTitle(getResources().getString(R.string.Copy_zone));
        dialog.setCancelable(false);

        TextView textviewZone1 = (TextView) dialog.findViewById(R.id.textviewZone1);
        TextView textviewZone2 = (TextView) dialog.findViewById(R.id.textviewZone2);
        TextView textviewZone3 = (TextView) dialog.findViewById(R.id.textviewZone3);
        textviewZone1.setText(AikosGlobals.getZones(pIndex).get(0).getName().toUpperCase());
        textviewZone2.setText(AikosGlobals.getZones(pIndex).get(1).getName().toUpperCase());
        textviewZone3.setText(AikosGlobals.getZones(pIndex).get(2).getName().toUpperCase());

        final RadioButton radiobuttonZone1 = (RadioButton) dialog.findViewById(R.id.radiobuttonZone1);
        final RadioButton radiobuttonZone2 = (RadioButton) dialog.findViewById(R.id.radiobuttonZone2);
        final RadioButton radiobuttonZone3 = (RadioButton) dialog.findViewById(R.id.radiobuttonZone3);
        radiobuttonZone1.setEnabled(zindex != 0);
        radiobuttonZone2.setEnabled(zindex != 1);
        radiobuttonZone3.setEnabled(zindex != 2);
        radiobuttonZone1.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiobuttonZone2.setChecked(false);
                radiobuttonZone3.setChecked(false);
                copyedIndex = 0;
            }
        });
        radiobuttonZone2.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiobuttonZone1.setChecked(false);
                radiobuttonZone3.setChecked(false);
                copyedIndex = 1;
            }
        });
        radiobuttonZone3.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiobuttonZone2.setChecked(false);
                radiobuttonZone1.setChecked(false);
                copyedIndex = 2;
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                doCopyZone(copyedIndex, zindex);
            }
        });

        copyedIndex = -1;

        dialog.show();
    }

    void doCopyZone(int zfrom, int zto) {
        if (zfrom < 0 || zto < 0 || zfrom == zto) {
            return;
        }
        AikosGlobals.setZoneTempObjs(AikosGlobals.getZoneTempObjsAWeek(zfrom, pIndex), zto, pIndex);
        AikosGlobals.getZones(pIndex).get(zto).setMergeInfo(AikosGlobals.getZones(pIndex).get(zfrom).getMergeInfo());
        ProgramEditFragment ff = (ProgramEditFragment) mainPagerAdapter.getItem(zto);
        ff.refreshView();
    }

    void onShowMergeZonesDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_merge_zone);
        dialog.setTitle(getResources().getString(R.string.Merge_zones));
        dialog.setCancelable(false);

        TextView textviewZone1 = (TextView) dialog.findViewById(R.id.textviewZone1);
        TextView textviewZone2 = (TextView) dialog.findViewById(R.id.textviewZone2);
        TextView textviewZone3 = (TextView) dialog.findViewById(R.id.textviewZone3);
        textviewZone1.setText(AikosGlobals.getZones(pIndex).get(0).getName().toUpperCase());
        textviewZone2.setText(AikosGlobals.getZones(pIndex).get(1).getName().toUpperCase());
        textviewZone3.setText(AikosGlobals.getZones(pIndex).get(2).getName().toUpperCase());

        final CheckBox checkboxZone1 = (CheckBox) dialog.findViewById(R.id.checkboxZone1);
        final CheckBox checkboxZone2 = (CheckBox) dialog.findViewById(R.id.checkboxZone2);
        final CheckBox checkboxZone3 = (CheckBox) dialog.findViewById(R.id.checkboxZone3);
        checkboxZone1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        checkboxZone2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        checkboxZone3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button buttonOK = (Button) dialog.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<Integer> mzones = new ArrayList<Integer>();
                if (checkboxZone1.isChecked()) mzones.add(0);
                if (checkboxZone2.isChecked()) mzones.add(1);
                if (checkboxZone3.isChecked()) mzones.add(2);
                doMergeZone(mzones);
            }
        });

        dialog.show();
    }

    void doMergeZone(ArrayList<Integer> mzones) {
        if (mzones.size() > 0) {

        }
    }
}
