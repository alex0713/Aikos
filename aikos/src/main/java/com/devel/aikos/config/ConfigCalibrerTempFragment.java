package com.devel.aikos.config;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.devel.aikos.R;
import com.devel.aikos.data.AikosGlobals;

public class ConfigCalibrerTempFragment extends Fragment {
	
	static final int ZONE_COUNT = 3;
	
	ListView listviewConfigCaltemp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_config_caltemp, container, false);
		
		listviewConfigCaltemp = (ListView) view.findViewById(R.id.listviewConfigCaltemp);
		listviewConfigCaltemp.setAdapter(new ConfigCaltempListAdapter());
		
		return view;
	}
	
	class ConfigCaltempListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 1 + ZONE_COUNT;
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
			
			Context context = ConfigCalibrerTempFragment.this.getActivity();
			
			if (view == null) {
				LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = li.inflate(R.layout.listitem_config_camtemp, arg2, false);
			}
			
			TextView textviewConfZone = (TextView) view.findViewById(R.id.textviewConfZone);
			TextView textviewConfMeasure = (TextView) view.findViewById(R.id.textviewConfMeasure);
			TextView textviewConfReal = (TextView) view.findViewById(R.id.textviewConfReal);
			
			if (position == 0) {
				view.setBackgroundColor(context.getResources().getColor(R.color.color_dark_gray));
				textviewConfZone.setText(context.getResources().getString(R.string.text_CAP_zone));
				textviewConfMeasure.setText(context.getResources().getString(R.string.text_CAP_measure));
				textviewConfReal.setText(context.getResources().getString(R.string.text_CAP_real));
			} else {
				view.setBackgroundColor(Color.WHITE);
				if (position > 0 && position <= AikosGlobals.getZones(0).size()) {
					textviewConfZone.setText(AikosGlobals.getZones(0).get(position - 1).getName());
				} else {
					textviewConfZone.setText("zone");
				}
				textviewConfMeasure.setText("22.5˚C");
				textviewConfReal.setText("23.4˚C");
			}
			
			return view;
		}
		
	}
}
