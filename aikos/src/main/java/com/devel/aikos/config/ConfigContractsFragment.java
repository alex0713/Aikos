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

import java.util.ArrayList;

public class ConfigContractsFragment extends Fragment {
	
	ListView listviewConfigContracts;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_config_contracts, container, false);
		
		
		listviewConfigContracts = (ListView) view.findViewById(R.id.listviewConfigContracts);
		listviewConfigContracts.setAdapter(new ConfigContractListAdapter());
		
		return view;
	}
	
	class ConfigContractListAdapter extends BaseAdapter {

        ArrayList<Integer> sections = new ArrayList<Integer>();

		@Override
		public int getCount() {
            int n = 0;
            sections.clear();
            for (int i = 0; i < AikosGlobals.contracts.size(); i++) {
                sections.add(n);
                n += AikosGlobals.contracts.get(i).getDataCount() + 1;
            }
            return n;
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
			
			Context context = ConfigContractsFragment.this.getActivity();
			
			if (view == null) {
				LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = li.inflate(R.layout.listitem_config_contracts, arg2, false);
			}
			
			TextView textviewConfigContractLabel = (TextView) view.findViewById(R.id.textviewConfigContractLabel);
			TextView textviewConfigContractData = (TextView) view.findViewById(R.id.textviewConfigContractData);
			
			int cate = position - sections.get(sections.size() - 1);
			int num = sections.size() - 1;
            sections.get(sections.size() - 1);
            for (int i = 0; i < sections.size(); i++) {
                if (position == sections.get(i)) {
                    cate = 0;
                    num = i;
                    break;
                } else if (position < sections.get(i)) {
                    if (i > 0) {
                        cate = position - sections.get(i - 1);
                        num = i - 1;
                    } else {
                        cate = position;
                        num = 0;
                    }
                    break;
                }
            }

            if (cate == 0) {
				view.setBackgroundColor(context.getResources().getColor(R.color.color_dark_gray));
				textviewConfigContractData.setVisibility(View.INVISIBLE);

                textviewConfigContractLabel.setText(AikosGlobals.contracts.get(num).name);
				
			} else  {
				view.setBackgroundColor(Color.WHITE);
				textviewConfigContractLabel.setText(AikosGlobals.contracts.get(num).getDataKey(cate - 1));
				textviewConfigContractData.setVisibility(View.VISIBLE);
				textviewConfigContractData.setText(AikosGlobals.contracts.get(num).getDataValue(cate - 1));
			}
			
			return view;
		}
		
	}
	
}
