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

public class ConfigAccessManageFragment extends Fragment {
	
	ListView listviewConfigAccessMan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_config_accessman, container, false);
		
		listviewConfigAccessMan = (ListView) view.findViewById(R.id.listviewConfigAccessMan);
		
		resetListView();
		
		return view;
	}
	
	private void resetListView() {
		listviewConfigAccessMan.setAdapter(new ConfigAccessManListAdapter(
				new String[] {
						"Laura  ", "Paul  ", "Jacques  "
				},
				new String[] {
						getActivity().getResources().getString(R.string.text_now), "hier, á 20:30  ", "mercredi 8 nov . "
				}));
	}
	
	class ConfigAccessManListAdapter extends BaseAdapter {
		
		String[] mnames = null;
		String[] mtimes = null;
		
		public ConfigAccessManListAdapter(String[] names, String[] times) {
			mnames = names;
			mtimes = times;
		}

		@Override
		public int getCount() {
			return mnames.length + 1;
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
			
			Context context = ConfigAccessManageFragment.this.getActivity();
			
			if (view == null) {
				LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = li.inflate(R.layout.listitem_config_accessman, arg2, false);
			}
			
			TextView textviewConfigAccessName = (TextView) view.findViewById(R.id.textviewConfigAccessName);
			TextView textviewConfigAccessTime = (TextView) view.findViewById(R.id.textviewConfigAccessTime);
			
			if (position == 0) {
				view.setBackgroundColor(context.getResources().getColor(R.color.color_dark_gray));
				textviewConfigAccessName.setText(context.getResources().getString(R.string.string_who_has_access___));
				textviewConfigAccessTime.setText(context.getResources().getString(R.string.string_when_was_the___));
			} else {
				String tstr = mtimes[position - 1];
				if (tstr.equalsIgnoreCase(getActivity().getResources().getString(R.string.text_now))) {
					textviewConfigAccessTime.setTextColor(getActivity().getResources().getColor(R.color.color_light_green));
				} else {
					textviewConfigAccessTime.setTextColor(getActivity().getResources().getColor(R.color.color_dark_gray));
				}
				view.setBackgroundColor(Color.WHITE);
				textviewConfigAccessName.setText(mnames[position - 1]);
				textviewConfigAccessTime.setText(mtimes[position - 1]);
			}
			
			return view;
		}
		
	}
}
